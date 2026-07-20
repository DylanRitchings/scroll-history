# Scroll History

Infinite-scroll feed of random European history articles, pulled live from
Wikipedia. Tap any card to read the full article in-app.

## How it works

- **Feed**: `HistoryFeedPagingSource` drives an endless `Paging 3` list. Each
  page load picks a random category from a curated list
  (`data/EuropeanHistoryCategories.kt`, ~35 categories spanning eras, regions,
  and themes) and a random alphabetical starting point within it, so the feed
  rarely repeats and never "runs out." Disambiguation pages and stubs with
  very short extracts are filtered out.
- **Detail**: tapping a card navigates to `DetailScreen`, which fetches the
  full plain-text extract + hero image for that article and shows a link back
  to the source Wikipedia page.
- No backend — the app talks directly to the public MediaWiki Action API at
  `en.wikipedia.org/w/api.php` (no API key needed).
- Stack: Kotlin, Jetpack Compose (Material 3), Paging 3, Retrofit + Gson,
  Coil for images.

## Building

This machine didn't have a JDK or the Android SDK installed, so I couldn't
compile or run this locally — I added `jdk17` and the `android-studio` cask
to your nix-darwin config (`nix/hosts/mac/modules/apps.nix`) but haven't run
`darwin-rebuild switch` yet. Once you've rebuilt and have Android Studio:

1. Open this folder in Android Studio (it'll pick up the Gradle wrapper
   automatically and prompt to install any missing SDK platforms/build
   tools).
2. Run on an emulator or device (`minSdk 26`, needs internet access).

Or from the command line once the SDK is set up:

```sh
./gradlew assembleDebug
./gradlew installDebug   # with a device/emulator connected
```

## Notes / things worth revisiting

- The random-category approach means very active categories (e.g. "World War
  II") show up more often than niche ones — could weight by category size if
  that gets noticeable.
- No offline caching yet; the feed refetches from scratch on process death.
- No tests included yet.
