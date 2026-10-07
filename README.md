OAuth2-jwt é uma aplicação backend desenvolvida para fins de aprendizado, demonstrando a implementação do login social do Google utilizando OAuth2, jwt — token de acesso e token de atualização.

A solução implementa segurança com um filtro de token CSRF personalizado — visto que o refresh token é armazenado em um cookie HttpOnly — e também inclui configuração de CORS.
