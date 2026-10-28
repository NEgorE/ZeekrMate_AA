# ZeekrMate R8 rules.

# Проверка подписи и менеджер активации не переименовываются — их логика должна
# читаться в стектрейсах и их легче защищать/обновлять.
-keep class com.zeekrmate.app.SignatureCheck { *; }
-keep class com.zeekrmate.app.LoginManager { *; }

# Line numbers в крэшах: сохраняем, чтобы стектрейсы оставались полезными.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
