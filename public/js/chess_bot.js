const pieceNames = new Map([

["p", "black_pawn"],
["r", "black_rook"],
["n", "black_knight"],
["b", "black_bishop"],
["q", "black_queen"],
["k", "black_king"],

["P", "white_pawn"],
["R", "white_rook"],
["N", "white_knight"],
["B", "white_bishop"],
["Q", "white_queen"],
["K", "white_king"]

]);


let field;

let startCoordinates = null;
let endCoordinates = null;


const boardSize = 8;
const playerColor = "w";
let game = new Chess();
updateField();


loadGame();


let board = document.getElementById("board");
board.style.width = "800px";
board.style.height = "800px";



for(let i = 0; i < boardSize; i++){
    let white = i%2 == 1;

    
    for(let j = 0; j < boardSize; j++){
        let field = document.createElement("button");

        const squareNumber = i*boardSize + j;
        const char = String.fromCharCode(j + 97);


        field.dataset.number = squareNumber;
        field.dataset.coordinates = char + (i - 8)*-1;
        field.dataset.col = i;
        field.dataset.row = j;

        white = !white;


        field.classList.add("field");

        field.addEventListener("click", function(){
            fieldClicked(field);
        })


        if(white){
            field.classList.add("whiteField");
        } else{
            field.classList.add("blackField");
        }

        


        board.appendChild(field);
    }

    
}

renderBoard();





function fieldClicked(field){
    let coordinates = field.dataset.coordinates;

    if(startCoordinates === null){
        startCoordinates = coordinates;
    } else{
        endCoordinates = coordinates;

        move();
    }
}


function move(){
    console.log("move: " + startCoordinates + " to " + endCoordinates);


    const moveResult = game.move({
        from: startCoordinates,
        to: endCoordinates,
        promotion: "q"
    });


    if(moveResult === null){
        console.error("Invalid move!");
        startCoordinates = null;
        endCoordinates = null;
        return;
    }

    startCoordinates = null;
    endCoordinates = null;

    sendMove(startCoordinates, endCoordinates);
    
    updateField();

    renderBoard();
}

function sendMove(startCoordinates, endCoordinates){
    
}


function updateField() {
    const boardString = game.fen().split(" ")[0];

    const splitedBoardString = boardString.split("/");

    field = [];

    for(let i = 0; i< boardSize; i++){
        const string = splitedBoardString[i];

        let arr = [];

        for(let j = 0; j<string.length; j++){
            let num = parseInt(string.charAt(j));
            if(isNaN(num)){
                arr.push(string.charAt(j));
            } else{
                for(let k = 0; k < num; k++){
                    arr.push(null);
                }
            }
        }

        field.push(arr);
    }
}



function renderBoard() {
    saveGame();

    let buttons = document.querySelectorAll(".field");

    buttons.forEach(button => {

        let row = button.dataset.row;
        let col = button.dataset.col;

        let piece = field[col][row];
        const pieceName = pieceNames.get(piece);


        button.innerHTML = "";

        if (piece !== null) {

            let img = document.createElement("img");

            
            img.src = "img/" + pieceName + ".png";
            

            button.appendChild(img);
        }
    });
}

function reset(){
    console.log("reseted");

    game = new Chess();

    updateField();

    renderBoard();

    startCoordinates = null;
    endCoordinates = null;
}


function saveGame() {
    localStorage.setItem("chessBoard", JSON.stringify(field));
}

function loadGame() {
    const saved = localStorage.getItem("chessBoard");
    if (!saved) return;
    if(saved == "undefined") return;

    field = JSON.parse(saved);

    renderBoard();
}


async function getBotMove() {
    await fetch(move);

    
}

