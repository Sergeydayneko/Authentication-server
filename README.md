# auth-backend

host:port/ws-dsp/v1/swagger - Страница для сваггера

Инструкция по работе со сваггером:

1. Находим раздел "Контроллер для работы с токенами"

2. Далее нажимаем на контроллер /v1/token

3. Заполняем логин и пароль: 
    1) login: sdayneko
    2) password: 123

        => Отправляем запрос 

4. Приходит 2 токена(token и refresh token). Необходимо скопировать token <h4>без кавычек</h4>

5. Далее нажимаем на кнопку authorize сверху справа.

6. В открывшемся окне в поле <h5>value</h5> вставляем <h4>Bearer <скопированный ранее токен> </h4> и
нажимаем на кнопку authorize

7. Поздравляю, теперь можно проверять контроллеры :)