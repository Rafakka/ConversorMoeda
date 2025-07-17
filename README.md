# 💱 Conversor de Moedas - Java + API

Este é um pequeno projeto em Java que consome uma API de câmbio para converter valores entre diferentes moedas. Foi desenvolvido como forma de praticar integração com APIs REST, manipulação de JSON com Gson e boas práticas de estruturação de projetos em Java.

## 🚀 Funcionalidades

- ✅ Conversão de valores entre diferentes moedas (ex: USD → BRL)
- 🔁 Entrada dinâmica via console
- 🌐 Integração com API externa (ExchangeRate API)
- 🔐 Uso de arquivo `config.properties` para armazenar a chave da API

## 🛠 Tecnologias

- Java 17
- Gson (para parsear JSON)
- API pública de câmbio (ex: https://v6.exchangerate-api.com/)
- Gradle ou build manual
- VSCode como IDE

## 🧱 Estrutura do Projeto

```
ConversorMoedas/
├── src/
│   ├── ConversionRecord.java
│   ├── CurrencyConverter.java
│   └── ConfigUtil.java
├── lib/
│   └── gson-2.10.1.jar
├── historico.json
├── settings.json
├── config.properties
└── README.md
```

## 📦 Como rodar

1. 💡 Certifique-se de ter o Java instalado (`java -version`)
2. 📥 Baixe o `gson.jar` e coloque na pasta `lib/`
3. 🔑 Crie um arquivo `config.properties` na raiz com:
   ```properties
   API_KEY=sua_chave_aqui
   ```
4. 🧱 Compile com:
   ```bash
   javac -cp "lib/gson-2.10.1.jar" src/*.java
   ```
5. ▶️ Rode com:
   ```bash
   java -cp "lib/gson-2.10.1.jar:src" Main
   ```

## 📁 .gitignore

Inclua isso no `.gitignore` para evitar o commit de dados sensíveis ou arquivos desnecessários:

```
/config.properties
/lib/
*.class
```

## ☕ Autor

Projeto criado por Rafael Kaher.
