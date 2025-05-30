Вы будете создавать сервис для шеринга (от англ. share — «делиться») вещей.

Ваш проект = ShareIt. Он должен обеспечить:
- возможность рассказывать, какими вещами они готовы поделиться,
- находить нужную вещь и брать её в аренду на определённые даты,
- закрывать к ней доступ на время бронирования
- если нужной вещи на сервисе нет, у пользователей должна быть возможность оставлять запросы.
- по запросу можно будет добавлять новые вещи для шеринга.

Каркас приложения
- Основная сущность сервиса — вещь Item.
- Пользователь, который добавляет новую вещь, будет считаться ее владельцем.

При добавлении вещи:
- возможность указать её краткое название и добавить небольшое описание.
К примеру, название может быть — «Дрель “Салют”», а описание — «Мощность 600 вт, работает ударный режим, так что бетон
возьмёт».
- У вещи должен быть статус — доступна ли она для аренды. Статус должен проставлять владелец.
- Для поиска вещей должен быть организован поиск.

Бронирование, или Booking — ещё одна важная сущность приложения.
- Бронируется вещь всегда на определённые даты.
- Владелец вещи обязательно должен подтвердить бронирование.
- После того как вещь возвращена, у пользователя возможность оставить отзыв.
- В отзыве можно поблагодарить владельца вещи и подтвердить, что задача выполнена.

Сущность запрос вещи ItemRequest.
- Пользователь создаёт запрос, указывается, что именно он ищет.
- В ответ на запрос другие пользовали могут добавить нужную вещь.

Реализация модели данных
- Структура не по типам классов, а по фичам (англ. Feature layout) — весь код с определённой сущностью в одном пакете.
- Поэтому сразу создайте четыре пакета — item, booking, request и user.

Создание DTO-объектов и мапперов
- Созданные объекты Item и User вы в дальнейшем будете использовать для работы с базой данных. Сейчас, помимо них, вам
также понадобятся объекты, которые вы будете возвращать пользователям через REST-интерфейс в ответ на их запросы.

- Разделять объекты, которые хранятся в базе данных и возвращаются пользователям, — хорошая практика. Например,
вы можете не захотеть показывать пользователям владельца вещи (поле owner), а вместо этого возвращать только информацию
о том, сколько раз вещь была в аренде. Чтобы это реализовать, нужно создать отдельную версию каждого класса, с которой
будут работать пользователи, — DTO (Data Transfer Object).

- Кроме DTO-классов, понадобятся Mapper-классы — они помогут преобразовывать объекты модели в DTO-объекты и обратно.
Для базовых сущностей Item и User создайте Mapper-класс и метод преобразования объекта модели в DTO-объект.

Разработка контроллеров
- Когда классы для хранения данных будут готовы, DTO и мапперы написаны, можно перейти к реализации логики. В приложении
будет три классических слоя — контроллеры, сервисы и репозитории. Пока, вы будете работать преимущественно с контроллерами.

- Для начала научите ваше приложение работать с пользователями. Ранее вы уже создавали контроллеры для управления
пользователями — создания, редактирования и просмотра. Здесь вам нужно сделать то же самое. Создайте класс UserController
и методы в нём для основных CRUD-операций. Также реализуйте сохранение данных о пользователях в памяти.

- Далее переходите к основной функциональности — работе с вещами. Вам нужно реализовать добавление новых вещей, их
редактирование, просмотр списка вещей и поиск. Создайте класс ItemController. В нём будет сосредоточен весь REST-интерфейс
для работы с вещью.

Основные сценарии:
- Добавление новой вещи. Будет происходить по эндпойнту POST /items. На вход поступает объект ItemDto. userId в заголовке
X-Sharer-User-Id — это идентификатор пользователя, который добавляет вещь. Именно этот пользователь — владелец вещи.
Идентификатор владельца будет поступать на вход в каждом из запросов, рассмотренных далее.

- Редактирование вещи. Эндпойнт PATCH /items/{itemId}. Изменить можно название, описание и статус доступа к аренде.
Редактировать вещь может только её владелец.

- Просмотр информации о конкретной вещи по её идентификатору. Эндпойнт GET /items/{itemId}. Информацию о вещи может
просмотреть любой пользователь.

- Просмотр владельцем списка всех его вещей с указанием названия и описания для каждой. Эндпойнт GET /items.
Поиск вещи потенциальным арендатором. Пользователь передаёт в строке запроса текст, и система ищет вещи, содержащие этот
текст в названии или описании. Происходит по эндпойнту /items/search?text={text}, в text передаётся текст для поиска.
Проверьте, что поиск возвращает только доступные для аренды вещи.

Для каждого из данных сценариев создайте соответственный метод в контроллере. Также создайте интерфейс ItemService и
реализующий его класс ItemServiceImpl, к которому будет обращаться ваш контроллер. В качестве DAO создайте реализации,
которые будут хранить данные в памяти приложения. Работу с базой данных вы чуть позже.

Тестирование
- Для проверки кода мы подготовили Postman-коллекцию. С её помощью можно протестировать ваше API и убедиться, что все
запросы успешно выполняются.