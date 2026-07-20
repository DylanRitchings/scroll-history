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
