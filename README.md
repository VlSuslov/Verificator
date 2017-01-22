# Verificator

<p>Верификатор данных для аккаунта G Suite из XLS файла. </p>
<p>Правила верификации определенны в файле specification.xml</p>
<h2>Требования к XLS файлу</h2>
<p>XLS файл должен содержать 9 заполненных столбцов в первой строке файла:</p>
<p>&nbsp;  <strong>First Name</strong> - Имя и отчество кириллицей</p>
<p>&nbsp;  <strong>Last Name</strong> - Фамилия кириллицей </p>
<p>&nbsp;  <strong>Email Address</strong> - Email адресс</p>
<p>&nbsp;  <strong>Password</strong> - Пароль к аккаунту</p>
<p>&nbsp;  <strong>Secondary Email</strong> -Дополнительный Email адресс</p>
<p>&nbsp;  <strong>Mobile Phone</strong>  - Мобильный телефон</p>
<p>&nbsp;  <strong>Department</strong> - Рабочий департамент</p>
<p>&nbsp;  <strong>First Name EN</strong> - Имя (без отчества) латиницей. </p>
<p>&nbsp;  <strong>Last Name EN</strong> - Фамилия латиницей.</p>
<h2>Требования к  файлу specification.xml</h2>
<p>Корневой элемент содержит 7 вложенных тэгов</p>
<p>&nbsp;<strong>firstName</strong>-содержит в себе регулярное выражение для проверки поля First Name</p>
<p>&nbsp;<strong>lastName</strong>-содержит в себе регулярное выражение для проверки поля Last Name</p>
<p>&nbsp;<strong>email-providers</strong>-содержит набор вложенных тэгов <strong>email-provider</strong> для проверки поля Secondary Email.
Имеет как минимум один вложенный тег <strong>email-provider</strong> для проверки Email адресса на корректность в соответсвии с RFC 822. 
Остальные тэги <strong>email-provider</strong> предназначены для проверки на корректность адресов конкретных доменов в соответствии 
с их требованиями.Имеют атрибут domen хранящий название домена провайдера. Внутри содержат регулярное выражение для проверки 
Email на корректность.</p>
<p>&nbsp;<strong>phones</strong>-содержит набор вложенных тегов <strong>phone</strong> для проверки корректности поля Mobile Phone. 
Тэг <strong>phone</strong> имеет атрибут name, содержащий название страны которой соответствует формат номера.
Внутри содержит регулярное выражение для проверки номера на корректность формату конкретной страны.</p>
<p>&nbsp;<strong>departments</strong>-содержит набор тегов <strong>departament</strong> для проверки корректности поля Department.
Имеет как минимум один вложенный элемент <strong>departament</strong> для проверки названия департамента по умолчанию. 
Остальные тэги  <strong>departament</strong> используются для проверки названия на соответсвия формату конкретных видов департаментов.
Тэг <strong>departament</strong> имеет атрибут name, содержащий название вида департаментов, на принадлежность к которым проверяется поле.
Содержит внутри себя регулярное выражения для проверки поля на принадлежность к виду департаментов.</p>
<p>&nbsp;<strong>firstNameEN</strong>-содержит в себе регулярное выражение для проверки поля First Name EN</p>
<p>&nbsp;<strong>lastNameEN</strong>-содержит в себе регулярное выражение для проверки поля Last Name EN </p>
<h2>Правила файла specification.xml по умолчанию</h2>
<p>&nbsp;<strong>firstName</strong>-Допускается только кириллические буквы (русский и украинский алфавит), 
символ дефиса для двойных имен, один пробел между именем и отчеством. Имя и отчество с большой буквы.</p>
<p>&nbsp;<strong>lastName</strong>- Кириллические символы и символ дефиса для двойных фамилий.С большой буквы.</p>
<p>&nbsp;<strong>email-providers</strong>-содержит 3 вложенных тега <strong>email-provider</strong>. 
Первый для проверки по формату RFC 822,
второй и третий для провайдеров gmail и yandex.</p>
<p>&nbsp;<strong>phones</strong>-содержит один вложенный тэг <strong>phone</strong> для номеров Украины.</p>
<p>&nbsp;<strong>departments</strong>-содержит 2 вложенных тэга <strong>department</strong>.
Первый для проверки формата по умолчанию и второй для учебных департаментов.</p>
<p>&nbsp;<strong>firstNameEN</strong>-Только имя английскими буквами, 
символ дефиса для двойных имен. С большой буквы.</p>
<p>&nbsp;<strong>lastNameEN</strong>-Фамилия английскими буквами, 
символ дефиса для двойных фамилий. С большой буквы.</p></p>




