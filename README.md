# FloriculturaTadesMobile
Repositório destinado para o projeto mobile, do sistema (WEB) da matéria de PI IV do SENAC Santo Amaro

# Como utilizar
- Será necessário ter instalado em sua maquina, as IDE's para poder executar os projetos em localhost
* Android Studio
* NetBeans (com Tomcat)
* MySql
* Xampp

- Ao instalar tudo, clone o projeto
- Você verá dois projetos na pasta, um é referente ao WebService em java
- Já o outro é referente ao projeto android

- Ao Abrir o projeto do WebService no NetBeans, você deverá seguir os seguintes passos:
* Importar as Jar's do JDBC Connector, JSON-Simple que se encontram na pasta Dependencies
* Pode ser que seja necessário criar a pasta 'WEB-INF' dentro do projeto, em alguns computadores
* Alterar a classe que conecta o projeto com o BD, colocando a mesma porta que seu Pc está usando do Tomcat

- Após ter feito isso, rode o Script do banco de dados na sua aplicação do Mysql Local

- Inicie o projeto android, pode rodar no emulador ou em seu celular fisico mesmo
- Lembre-se de alterar o caminho do WebService com o seu próprio IPLocal, para que funcione corretamente
