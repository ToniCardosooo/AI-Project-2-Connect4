# Inteligência Artificial - 2022/2023

## Projeto 2 - Jogo dos Quatro em Linha

### Vista Geral

Este projeto foi desenvolvido no âmbito da Unidade Curricular *Inteligência Artificial*, durante o ano letivo 2022/2023, na Faculdade de Ciências da Universidade do Porto, pelos alunos António Cardoso, Bárbara Santos e Isabel Brito.

O seu principal objetivo é a implementação completa do [Jogo dos Quatro em Linha](https://en.wikipedia.org/wiki/Connect_Four "Descrição do Jogo dos 4 em Linha - Wikipédia (Inglês)") e de diferentes algoritmos de pesquisa que procurem a melhor jogada, mediante o estado atual do jogo. Para tal, foi escolhida a linguagem Java, devido à sua relação com Programação Orientada a Objetos, o facto de ser *strongly-typed*, o seu "*Garbage Collector*", entre outros motivos.

O programa foi compilado tanto em *Ubuntu 20.04 LTS* com *javac 11.0.17* como *macOS Monterey version 12.2.1* com *javac 18.0.2.1*.

### Instruções de Compilação e Execução

Para poder executar cada problema é primeiro necessário compilar todos os ficheiros java. Para tal, utilize a seguinte instrução:

`javac *.java`

Após a compilação de todos os ficheiros, é possível, finalmente, executá-los. Foram desenvolvidos os seguintes algoritmos:
- Minimax
- Minimax com cortes Alpha-Beta
- Monte Carlo Tree Search

Para a execução de cada um, utilizar o seguinte formato de entrada `java Game type`, onde "type" deve ser substituido pelo nome do algoritmo desejado:
- `java Game MiniMax`
- `java Game AlphaBeta`
- `java Game MCTS`

O programa irá aguardar o primeiro movimento do utilizador. O mesmo deve ser fornecido com um número de 1 a 7, representante da coluna onde se quer jogar. 

Exemplo:

`It is now X's turn.`  
`Make a move by choosing your coordinate to play (1 to 7):`

`2`

É esperado que, após a jogada do utilizador, seja impresso o tabuleiro atual e, de seguida, o tabuleiro com a jogada do algoritmo adversário. Isto repete-se até que um dos lados ganhe ou o tabuleiro fique cheio, com um empate.

