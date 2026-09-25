# DemoTaskApp — Modern Android News Application

**DemoTaskApp** — оновлений Android-додаток для перегляду новин, розроблений за принципами **Clean Architecture**, **MVI (Model-View-Intent)** та сучасними гайдлайнами Google (Jetpack Compose, Koin, Coroutines, Flow).

---

## 🏗️ Архітектура та структура проекту

Проект побудований на принципах **Clean Architecture** з виділенням шарів у модулі для швидкої паралельної збірки та чіткого розмежування відповідальностей.

```
DemoTaskApp
├── app/                        # Головний модуль: точки входу (MainActivity, App), DI граф, тема
├── core/
│   ├── domain/                 # Доменні моделі (Article, NewsCategory), інтерфейс репозиторія, UseCases
│   ├── data/                   # Реалізація репозиторія (NewsArticleRepositoryImpl), мапери
│   ├── database/               # Room Database (ArticleDatabase, DAO, ArticleEntity)
│   ├── network/                # Retrofit API сервіс (NewsApiService, DTOs, OkHttp)
│   ├── navigation/             # AppNavHost & BottomTabNavHost обгортки над Navigation Compose
│   └── presentation/           # BaseViewModel (MVI), UI події, LocalSnackbarHostState
└── feature/
    ├── main/ (api & presentation)        # Контейнер нижньої навігації (Home, Categories)
    ├── home/ (api, data & presentation)  # Головний екран стрічки новин та пошуку
    └── categories/ (api & presentation)  # Екран перегляду новин за категоріями
```

---

## 💡 Ключові концепції та паттерни

### 1. Clean Architecture & Domain Use Cases
* **Domain Layer (`:core:domain`)**:
  * Містить специфічні сценарії використання (**Use Cases**): `GetHomeNewsUseCase`, `GetCategoryNewsUseCase`, `SearchNewsUseCase`.
  * **Presentation Layer (ViewModel)** працює виключно через **Use Cases**, не маючи прямої залежності від розробки репозиторію.
  * Спільні доменні сутності (`Article`, `NewsCategory`) та контракт репозиторію розташовані в `:core:domain` для уникнення дублювання між фічами (`:feature:home` та `:feature:categories`).

### 2. MVI (Model-View-Intent) & Unidirectional Data Flow
Усі екрани використовують реактивний шаблон **MVI** на базі `BaseViewModel`:
* **State (`StateFlow`)**: Єдине незмінне джерело правди для UI.
* **Action**: Дії користувача (кліки, пошук, свайпи, завантаження).
* **NavEvents (`SharedFlow`)**: Одноразові події навігації.
* **UiEvents (`Channel`)**: Снаки, тости та повідомлення про помилки.

### 3. Offline-First & Smart Caching Strategy
* **Room Database**: Усі завантажені статті зберігаються в локальній БД.
* **Збереження закладок**: Метод `@Transaction saveArticlesPreservingBookmarks` гарантує, що при оновленні стрічки з мережі статус закладок користувача не втрачається.
* **Fallback Mode**: Якщо API ключ відсутній або немає з'єднання з мережею, додаток автоматично демонструє оффлайн-демо дані українською мовою.

---

## 🛠️ Стек технологій

* **Language**: Kotlin 2.4.20 / JDK 17
* **UI**: Jetpack Compose (Material 3, Navigation Compose, Coil)
* **DI**: Koin 4.2.2 (Koin BOM, Compose & Navigation integration)
* **Async & Reactive**: Kotlin Coroutines & StateFlow/SharedFlow
* **Network**: Retrofit 3.0.0, Moshi Converter, OkHttp 5
* **Database**: Room 3 (AndroidX Room)

---

## 🧪 Запуск та збірка

Збірка проекту здійснюється через Gradle:
```bash
./gradlew :app:assembleDebug
```
Усі юніт-тести можна запустити командою:
```bash
./gradlew test
```
