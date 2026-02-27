# Enio Comilão: A Grande Jornada Gastronômica

![Status](https://img.shields.io/badge/Status-Finalizado-success)
![Versão](https://img.shields.io/badge/Versão-1.1-blue)

**Enio Comilão** é um RPG de aventura 2D desenvolvido em **Java** com o framework **LibGDX**. Explore reinos inspirados em doces, enfrente os desafios da UFBA e ajude o Enio em sua missão épica e faminta!

---

## 🎮 Como Baixar e Jogar

Para rodar o jogo, siga os passos abaixo:

1. Acesse a aba [Releases](https://github.com/gustavo10gloria-code/Meu-primeiro-Grande-Projeto/releases/tag/1.1).
2. Baixe o arquivo `Enio.Comilao.rar`.
3. Extraia o conteúdo em uma pasta de sua preferência.
4. Execute o arquivo `Enio Comilão-1.0.jar`.

> **Nota:** Certifique-se de que o arquivo `.jar` e as pastas de recursos (Sound, Enio, Backgrounds, etc.) estejam na mesma pasta após a extração.

---

## 💾 Tecnologias
* **Core:** Java 25
* **Engine:** LibGDX (LWJGL3)
* **Build Tool:** Gradle
* **Design:** Pixel Art 16-bit

---

## 🛠️ Implementações e Desafios Técnicos

Para este projeto, foquei em aplicar conceitos de Engenharia de Software que garantissem a escalabilidade e a manutenção do código:

* **Gerenciamento de Estados (State Management):** Utilizei a estrutura de `Screens` do LibGDX para separar as responsabilidades de cada cenário (Menu, Exploração, Combate), facilitando a transição fluida entre os diferentes reinos.
* **Persistência de Dados Personalizada:** Implementei um `GerenciadorSave` que utiliza a API `Preferences` do LibGDX para serializar o progresso do jogador (Capítulo atual e status) de forma persistente, permitindo que a jornada seja retomada após o fechamento do executável.
* **Manipulação de Assets Internos vs Externos:** Resolvi desafios de carregamento de recursos no ambiente de produção (JAR), utilizando `Gdx.files.internal` para garantir que o sistema de arquivos fosse mapeado corretamente entre a IDE e o executável final.
* **Sistema de Diálogos Modular:** Estruturei as interações com NPCs de forma que novos textos e escolhas pudessem ser adicionados sem a necessidade de reescrever a lógica principal da tela.
* **Otimização de Performance:** Controle de taxa de quadros (Vsync) e gerenciamento de memória através do `dispose()` de texturas e sons, evitando vazamentos de memória (memory leaks) durante longas sessões de jogo.

---

## 👨‍💻 Autor
Desenvolvido por **Gustavo Santana**.
* "Se o Enio não parou de comer, eu não parei de codar!"
