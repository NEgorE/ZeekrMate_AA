# ZeekrMate_AA — движок Android Auto для ZeekrMate

Беспроводной Android Auto для Zeekr 7X: BT-хендшейк, Wi-Fi Direct (2.4/5 ГГц), TLS/AA-протокол, HEVC/H.264 декодер (NDK), аудио, ввод. Эта часть входит в состав ZeekrMate и лицензируется отдельно как производная [Open Headunit](https://github.com/andreknieriem/open-headunit) — **AGPL-3.0**.

Форк Open Headunit 3.5.0-beta5, адаптированный под Zeekr 7X: вырезан собственный UI-онбординг, интегрирован в ZeekrMate, добавлены исправления для платформы ECARX/Zeekr (DTM/HUD/ключ-телефон не затрагиваются этим репозиторием).

## Что здесь

| Папка | Содержимое |
|---|---|
| `app/src/main/java/com/andrerinas/openheadunit/` | Kotlin-исходники движка: `aap/` (протокол AA), `connection/` (BT/Wi-Fi Direct/WPP), `decoder/` (видео), `ssl/`, `view/`, `utils/` |
| `app/src/main/cpp/` | Нативный код: ffmpeg-декодер HEVC/H.264, aaudio-вывод, CMakeLists.txt |
| `app/src/main/jniLibs/` | Предсобранные `.so` (arm64-v8a, armeabi-v7a, x86, x86_64) |
| `app/src/main/aidl/` | IShizuku.aidl (интеграция с Shizuku) |
| `app/src/main/res/raw/` | TLS-сертификаты и ключ из Open Headunit (публичные, из апстрима) |
| `app/build.gradle.kts.reference` | Пример секций `externalNativeBuild`/`ndkVersion`/`buildFeatures.aidl` для интеграции |

## Сборка

Требуется Android SDK, NDK 29.0.14206865 и JDK 17+:

```text
externalNativeBuild { cmake { path("src/main/cpp/CMakeLists.txt"); version = "3.22.1" } }
ndkVersion = "29.0.14206865"
buildFeatures { aidl = true; buildConfig = true }
```

## Лицензия

AGPL-3.0 — унаследована от Open Headunit. Производные работы обязаны оставаться открытыми.
