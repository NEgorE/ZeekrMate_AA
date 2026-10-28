package com.andrerinas.openheadunit.connection.wifi.direct

/**
 * How long to leave the P2P stack alone after bring-up rounds that never formed a group.
 *
 * The phone re-dials its Bluetooth handshake every ten seconds, and each re-dial can land back in
 * a full bring-up — a remove, five creates, a standard fallback. A stack that answers every one of
 * those with BUSY or ERROR is therefore handed the whole round again every fifteen seconds,
 * indefinitely. The rounds cannot fix such a wedge: it clears on its own or at boot. What the
 * churn does cost is radio time on the combo chip, where Bluetooth Low Energy — on this platform,
 * the phone-as-key — starves while it runs.
 *
 * Pure and unit-tested; the counters live in [WifiDirectManager].
 */
object P2pBringUpBackoffPolicy {

    /**
     * Rest before the next full bring-up after N consecutive fully-failed rounds. Index 0 is
     * unused: one failed round costs no wait yet, since a single refusal is ordinary.
     */
    val REST_AFTER_ROUND_MS = longArrayOf(0L, 30_000L, 60_000L, 120_000L, 300_000L)

    /** Rounds past the table keep resting this long rather than escalating forever. */
    const val MAX_REST_MS = 300_000L

    /** The wait a bring-up owes after [failedRounds] consecutive rounds that formed no group. */
    fun restAfter(failedRounds: Int): Long =
        REST_AFTER_ROUND_MS.getOrElse(failedRounds) { MAX_REST_MS }.coerceAtMost(MAX_REST_MS)

    /**
     * Whether a bring-up may start now.
     *
     * [failedRounds] counts consecutive rounds that never formed a group; any group formed resets
     * the count, so a healthy stack is never held off. [lastFailureAtMs] of 0 means no failure has
     * been recorded in this process.
     */
    fun mayStart(failedRounds: Int, lastFailureAtMs: Long, now: Long): Boolean =
        failedRounds <= 0 || lastFailureAtMs == 0L ||
            now - lastFailureAtMs >= restAfter(failedRounds)
}
