# SuicideReporter

自殺報告者

![Android](https://img.shields.io/static/v1?label=platform&message=Android&color=green)
![Kotlin](https://img.shields.io/static/v1?label=language&message=Kotlin&color=orange)

![SuicideReporter](SuicideReporter.gif)

## なぁにこれぇ?

アプリがクラッシュした際にそのスタックトレースを自分で拾ってログに書き出すアプリです。  
[Hyperion-Android](https://github.com/willowtreeapps/Hyperion-Android) の [Hyperion-Crash](https://github.com/willowtreeapps/Hyperion-Android/tree/develop/hyperion-crash) を参考にしています。

## 実現方法

`Thread.setDefaultUncaughtExceptionHandler()` で設定する例外のハンドラ内でServiceを立ち上げているだけです。

```Kotlin
internal object CrashReporter {

    fun setup(context: Context) {
        val newHandler = when (val existingHandler = Thread.getDefaultUncaughtExceptionHandler()) {
            null -> Handler(context)
            else -> Wrapper(existingHandler, Handler(context))
        }

        Thread.setDefaultUncaughtExceptionHandler(newHandler)
    }

    private class Handler(private val context: Context) : Thread.UncaughtExceptionHandler {

        override fun uncaughtException(t: Thread, e: Throwable) {
            CrashReporterService.start(this.context, e)
        }
    }

    private class Wrapper(
        private val existing: Thread.UncaughtExceptionHandler,
        private val handler: Handler
    ) : Thread.UncaughtExceptionHandler {

        override fun uncaughtException(t: Thread, e: Throwable) {
            this.handler.uncaughtException(t, e)
            this.existing.uncaughtException(t, e)
        }
    }
}
```

参考: https://github.com/willowtreeapps/Hyperion-Android/blob/develop/hyperion-crash/src/main/java/com/willowtreeapps/hyperion/crash/CrashPlugin.java

このアプリではForegroundServiceを立ち上げていますが、Hyperion-CrashではActivityを立ち上げています。  

## 余談

### スタックトレースの文字列化について

`Throwable` からスタックトレース文字列を整形してくれるメソッドが用意されていました。  
`android.util.Log.getStackTraceString(Throwable?)` ← これです。

HyperionではStringBuilderを使って組み立てていましたが、私はこれで文字列化しました。

### 実行端末の情報について

Hyperion-Crashではユーザの端末の情報 (どのメーカのどのモデルを使っているか) も表示してくれていたと思います。  
それらは ↓ のように取得するみたいです。

参考: https://github.com/willowtreeapps/Hyperion-Android/blob/5f7901ffe1b376e63f904bdbbea4af2143b42bb7/hyperion-crash/src/main/java/com/willowtreeapps/hyperion/crash/ReportFactory.java

実際にこのようなクラッシュレポート機構を組み込む際にはレポートに付加するといいんじゃないでしょうか。😎

### adb pull コマンドについて

このアプリではログファイルをアプリ内で使えるストレージに保存しています。  
(そのパスは以下のコードで取得できます。)

```Kotlin
File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
```

そして、例えば以下の場所に保存されたとします。

ログファイルの場所: `/storage/emulated/0/Android/data/net.aridai.suicidereporter/files/Documents/2020-07-17-23-52-45.log`

この保存場所をPCで見るにはターミナルで以下を実行すればいいみたいです。

```
adb pull  /storage/emulated/0/Android/data/net.aridai.suicidereporter/files/Documents/ ~/作業フォルダだよ/
```

ホームディレクトリの `作業フォルダだよ` に保存されます。  
`adb pull` コマンドは便利ですにぇ。
