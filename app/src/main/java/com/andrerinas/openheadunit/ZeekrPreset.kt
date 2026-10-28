package com.andrerinas.openheadunit

import android.content.Context
import com.andrerinas.openheadunit.main.OnboardingActivity
import com.andrerinas.openheadunit.connection.wifi.WifiLauncherMode
import com.andrerinas.openheadunit.utils.Settings

/**
 * Преднастройка Zeekr 7x: применяется при каждом старте приложения, идемпотентно.
 *
 * Машина одна (Zeekr 7x), поэтому пользовательской конфигурации нет — всё, что
 * найдено при отладке беспроводного Android Auto, зафиксировано здесь:
 *  - онбординг-мастер пропускается (машина настраивается сама);
 *  - Wi-Fi Direct группа поднимается ТОЛЬКО на 2.4 ГГц: телефон не видит каналы
 *    149+ (UNII-3), на которых драйвер машины поднимает группу по умолчанию;
 *  - режим подключения — Native AA wireless (это и так дефолт, фиксируем явно).
 *
 * 5 ГГц проверяли 2026-10-24: группа честно поднимается на 5765 МГц (канал 153,
 * UNII-3), но телефон её не видит — выбор диапазона убран, 2.4 ГГц зафиксирован.
 *
 * Разрешение и DPI — пользовательские настройки, их пресет не трогает.
 */
object ZeekrPreset {

    fun apply(context: Context) {
        runCatching {
            val settings = Settings(context)
            // онбординг-мастер не нужен: всё конфигурируется этим пресетом
            settings.onboardingVersion = OnboardingActivity.CURRENT_ONBOARDING_VERSION
            // Wi-Fi Direct: только 2.4 ГГц (иначе группа встанет на каналах,
            // невидимых телефону)
            settings.wifiDirectBand = 2
            // Native AA wireless — единственный поддерживаемый режим
            settings.wifiConnectionMode = WifiLauncherMode.NATIVE
        }
    }
}
