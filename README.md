# Sudoku

Projeto desenvolvido durante o bootcamp Orange Tech+ Back-End da DIO. Implementação do jogo Sudoku em Java com duas interfaces distintas: modo console via terminal e modo gráfico com Java Swing.

## Sobre o projeto

O jogo carrega um tabuleiro pré-configurado via argumentos de linha de comando, onde cada posição recebe um valor esperado e uma flag indicando se a célula é fixa ou editável. O jogador preenche as células livres e pode verificar o status, reiniciar ou finalizar o jogo a qualquer momento.

## Stack

- Java 21
- Java Swing

## Funcionalidades

Modo console:
- Iniciar novo jogo
- Inserir e remover números
- Visualizar tabuleiro atual
- Verificar status e erros
- Reiniciar e finalizar jogo

Modo gráfico:
- Tabuleiro interativo com validação de entrada
- Apenas dígitos de 1 a 9 são aceitos
- Células fixas bloqueadas para edição
- Botões para verificar status, reiniciar e finalizar
- Notificação via dialog ao concluir o jogo

## Arquitetura

O projeto é organizado em camadas com responsabilidades separadas.

```
src/main/java/org/alexvsi/sudoku/
├── model/
│   ├── Board.java              # Lógica do tabuleiro e regras do jogo
│   ├── Space.java              # Representação de cada célula
│   └── GameStatusEnum.java     # Estados do jogo
├── service/
│   ├── BoardService.java       # Orquestração entre UI e domínio
│   ├── NotifierService.java    # Implementação do padrão Observer
│   ├── EventListener.java      # Interface do Observer
│   └── EventEnum.java          # Tipos de evento
├── ui/
│   └── custom/                 # Componentes Swing customizados
└── util/
    └── BoardTemplate.java      # Template do tabuleiro para o console
```

## Padrão Observer

A comunicação entre componentes da interface usa o padrão Observer. O `NotifierService` mantém uma lista de `EventListener` por tipo de evento. Quando o jogador reinicia o jogo, o serviço notifica todos os campos de texto registrados, que limpam seu conteúdo automaticamente sem acoplamento direto entre os componentes.

## Como executar

**Requisitos:** Java 21

O tabuleiro é configurado via argumentos no formato `coluna,linha;valorEsperado,fixo`:

```bash
java -cp target/sudoku.jar org.alexvsi.sudoku.Main \
  "0,0;4,false" "1,0;7,false" "2,0;9,true" "3,0;5,false" "4,0;8,true" "5,0;6,true" "6,0;2,true" "7,0;3,false" "8,0;1,false" \
  "0,1;1,false" "1,1;3,true" "2,1;5,false" "3,1;4,false" "4,1;7,true" "5,1;2,false" "6,1;8,false" "7,1;9,true" "8,1;6,true" \
  "0,2;2,false" "1,2;6,true" "2,2;8,false" "3,2;9,false" "4,2;1,true" "5,2;3,false" "6,2;7,false" "7,2;4,false" "8,2;5,true" \
  "0,3;5,true" "1,3;1,false" "2,3;3,true" "3,3;7,false" "4,3;6,false" "5,3;4,false" "6,3;9,false" "7,3;8,true" "8,3;2,false" \
  "0,4;8,false" "1,4;9,true" "2,4;7,false" "3,4;1,true" "4,4;2,true" "5,4;5,true" "6,4;3,false" "7,4;6,true" "8,4;4,false" \
  "0,5;6,false" "1,5;4,true" "2,5;2,false" "3,5;3,false" "4,5;9,false" "5,5;8,false" "6,5;1,true" "7,5;5,false" "8,5;7,true" \
  "0,6;7,true" "1,6;5,false" "2,6;4,false" "3,6;2,false" "4,6;3,true" "5,6;9,false" "6,6;6,false" "7,6;1,true" "8,6;8,false" \
  "0,7;9,true" "1,7;8,true" "2,7;1,false" "3,7;6,false" "4,7;4,true" "5,7;7,false" "6,7;5,false" "7,7;2,true" "8,7;3,false" \
  "0,8;3,false" "1,8;2,false" "2,8;6,true" "3,8;8,true" "4,8;5,true" "5,8;1,false" "6,8;4,true" "7,8;7,false" "8,8;9,false"
```

Para o modo gráfico:

```bash
java -cp target/sudoku.jar org.alexvsi.sudoku.UIMain \
  "0,0;4,false" "1,0;7,false" "2,0;9,true" "3,0;5,false" "4,0;8,true" "5,0;6,true" "6,0;2,true" "7,0;3,false" "8,0;1,false" \
  "0,1;1,false" "1,1;3,true" "2,1;5,false" "3,1;4,false" "4,1;7,true" "5,1;2,false" "6,1;8,false" "7,1;9,true" "8,1;6,true" \
  "0,2;2,false" "1,2;6,true" "2,2;8,false" "3,2;9,false" "4,2;1,true" "5,2;3,false" "6,2;7,false" "7,2;4,false" "8,2;5,true" \
  "0,3;5,true" "1,3;1,false" "2,3;3,true" "3,3;7,false" "4,3;6,false" "5,3;4,false" "6,3;9,false" "7,3;8,true" "8,3;2,false" \
  "0,4;8,false" "1,4;9,true" "2,4;7,false" "3,4;1,true" "4,4;2,true" "5,4;5,true" "6,4;3,false" "7,4;6,true" "8,4;4,false" \
  "0,5;6,false" "1,5;4,true" "2,5;2,false" "3,5;3,false" "4,5;9,false" "5,5;8,false" "6,5;1,true" "7,5;5,false" "8,5;7,true" \
  "0,6;7,true" "1,6;5,false" "2,6;4,false" "3,6;2,false" "4,6;3,true" "5,6;9,false" "6,6;6,false" "7,6;1,true" "8,6;8,false" \
  "0,7;9,true" "1,7;8,true" "2,7;1,false" "3,7;6,false" "4,7;4,true" "5,7;7,false" "6,7;5,false" "7,7;2,true" "8,7;3,false" \
  "0,8;3,false" "1,8;2,false" "2,8;6,true" "3,8;8,true" "4,8;5,true" "5,8;1,false" "6,8;4,true" "7,8;7,false" "8,8;9,false"
```
