function compile_code() {
    var code = document.getElementById("input_textarea").value;

    $.ajax({
        type: "POST",
        url: "/compile",
        data: code,
        contentType: "text/plain",
        success: function(response) {
            // console.log(response);
            // document.getElementById("output").value = response;
        },
        error: function(xhr, status, error) {
            // console.log("error");
            // console.error(xhr.responseText);
        }
    });
}

function send_last_input() {
    var outputTextarea = document.getElementById("output");
    var lines = outputTextarea.value.split("\n");
    var lastLine = lines[lines.length - 1];

    $.ajax({
        type: "POST",
        url: "/input",
        data: lastLine,
        contentType: "text/plain",
        success: function(response) {
            console.log("Последняя строка успешно отправлена на сервер");
        },
        error: function(xhr, status, error) {
            console.error("Ошибка при отправке последней строки на сервер:", xhr.responseText);
        }
    });
}

document.addEventListener("DOMContentLoaded", function() {
    var textarea = document.getElementById("input_textarea")
    var input_lines = document.getElementById("lines");

    document.getElementById("output").addEventListener("keypress", function(event) {
        if (event.key === "Enter") {
            send_last_input();
        }
    });
    textarea.addEventListener("input", function() {
        updateInputLines();
    });
    textarea.addEventListener("scroll", updateInputLines);
    // textarea.addEventListener("mousemove", function() {
    //     updateCursorPosition();
    // });
    
    // textarea.addEventListener("mousedown", function() {
    //     updateCursorPosition();
    // });
    // textarea.addEventListener("mouseup", function() {
    //     updateCursorPosition();
    // });

    // textarea.addEventListener("keyup", function() {
    //     updateCursorPosition();
    // });
    
    textarea.addEventListener("keydown", function(event) {
        if (event.key == "Tab" && !event.shiftKey) {
            event.preventDefault();
            var cursorPosition = this.selectionStart;
            var textBeforeCursor = this.value.substring(0, cursorPosition);
            var textAfterCursor = this.value.substring(cursorPosition, this.value.length);
            this.value = textBeforeCursor + "    " + textAfterCursor;
      
            this.selectionStart = cursorPosition + 4;
            this.selectionEnd = cursorPosition + 4;
        } else if (event.key == "Tab" && event.shiftKey) {
            event.preventDefault();
            var cursorPosition = this.selectionStart;
            var textBeforeCursor = this.value.substring(0, cursorPosition);
            var textAfterCursor = this.value.substring(cursorPosition, this.value.length);
            var spacesToRemove = 0;
            if (cursorPosition >= 4 && textBeforeCursor.substring(cursorPosition - 4, cursorPosition) == "    ") {
                textBeforeCursor = textBeforeCursor.substring(0, cursorPosition - 4);
                spacesToRemove = 4;
            }
            this.value = textBeforeCursor + textAfterCursor;
            this.selectionStart = cursorPosition - spacesToRemove;
            this.selectionEnd = cursorPosition - spacesToRemove;
        }
    });
});

var socket = new SockJS('/end-point');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
	// console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', function(message) {
        // console.log('Received message from server:', message.body);
        var messageText = message.body;
        appendTextToOutput(messageText);
    });
});

function appendTextToOutput(text) {
    var outputTextArea = document.getElementById("output");
    text = text.replace(/\r/g, "");
    outputTextArea.value += text;
}

var change = false;

var keywords = [
    "abstract", "continue", "for", "new", "switch",
    "assert", "default", "if", "package", "synchronized",
    "boolean", "do", "goto", "private", "this",
    "break", "double", "implements", "protected", "throw",
    "byte", "else", "import", "public", "throws",
    "case", "enum", "instanceof", "return", "transient",
    "catch", "extends", "int", "short", "try",
    "char", "final", "interface", "static", "void",
    "class", "finally", "long", "strictfp", "volatile",
    "const", "float", "native", "super", "while",
    "null", "true", "false"
];

function updateInputLines() {
    var textarea = document.getElementById("input_textarea");
    var inputLinesDiv = document.getElementById("lines");
    var lines = textarea.value.split("\n");
    var comment_multiline = false;
    var comment_singleline = false;
    var one_quot = false;
    var two_quot = false;

    inputLinesDiv.innerHTML = "";

    var hasHorizontalScrollbar = textarea.scrollWidth > textarea.clientWidth;
    var hasVerticalScrollbar = textarea.scrollHeight > textarea.clientHeight;

    if (hasHorizontalScrollbar && hasVerticalScrollbar && !change) {
        inputLinesDiv.classList.add("input_lines_change");
        inputLinesDiv.classList.remove("input_lines");
        change = true;
    } else if ((!hasHorizontalScrollbar || !hasVerticalScrollbar) && change) {
        inputLinesDiv.classList.add("input_lines");
        inputLinesDiv.classList.remove("input_lines_change");
        change = false;
    }

    // Строки
    for (var i = 0; i < lines.length; i++) {
        // Поиск табуляций в начале
        var lineDiv = document.createElement("div");    
        lineDiv.classList.add("line");
        if (lines[i] != "") {
            var wordIndex = 0;
            while (lines[i][wordIndex] === " ") {
                wordIndex++;
            }
            var division = Math.floor(wordIndex / 4)
            var rem = wordIndex % 4
            if (division > 0) {
                if (rem > 0) {
                    for (var k = 0; k < division; k++) {
                        var span = document.createElement("span");
                        span.classList.add("tab");
                        span.innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;";
                        lineDiv.appendChild(span);
                    }
                } else if (rem == 0) {
                    for (var k = 0; k < division-1; k++) {
                        var span = document.createElement("span");
                        span.classList.add("tab");
                        span.innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;";
                        lineDiv.appendChild(span);
                    }
                }
            }

            // Обработка строк
            var count = 0;
            var text = "";
            var start;
            if (division == 0 && rem == 0) {
                start = 0;
            } else if (division > 0 && rem == 0) {
                start = (division - 1) * 4;
            } else {
                start = division * 4;
            }

            for (var j = start; j < lines[i].length; j++) {
                if (!comment_multiline && !comment_singleline && !one_quot && !two_quot) {
                    if (lines[i][j] == " ") {
                        if (text != "") {
                            var span = document.createElement("span");
                            if (keywords.includes(text)) {
                                span.classList.add("keyword");
                            }
                            span.textContent = text;
                            span.style.marginLeft = (count * 8.8) + 'px';
                            lineDiv.appendChild(span);
    
                            count = 0;
                            text = "";
                        }
                        count += 1;
                    } else {
                        text += lines[i][j];
                        if (text.length == 2 && text.slice(-2) == "/*") {
                            comment_multiline = true;

                            text = text.slice(-2);
                        } else if (text.length > 2 && text.slice(-2) == "/*") {
                            comment_multiline = true;
    
                            var span = document.createElement("span");
                            if (keywords.includes(text.slice(0, -2))) {
                                span.classList.add("keyword");
                            }
                            span.textContent = text.slice(0, -2);
                            span.style.marginLeft = (count * 8.8) + 'px';
                            lineDiv.appendChild(span);
    
                            text = text.slice(-2);
                            count = 0;
                        } else if (text.length == 2 && text.slice(-2) == "//") {
                            comment_singleline = true;
                        } else if (text.length > 2 && text.slice(-2) == "//") {
                            comment_singleline = true;

                            var span = document.createElement("span");
                            if (keywords.includes(text.slice(0, -2))) {
                                span.classList.add("keyword");
                            }
                            span.textContent = text.slice(0, -2);
                            span.style.marginLeft = (count * 8.8) + 'px';
                            lineDiv.appendChild(span);

                            text = text.slice(-2);
                            count = 0;
                        } else if (text.length == 1 && text == '"') {
                            two_quot = true;
                        } else if (text.length > 1 && text.slice(-1) == '"') {
                            two_quot = true;

                            var span = document.createElement("span");
                            if (keywords.includes(text.slice(0, -1))) {
                                span.classList.add("keyword");
                            }
                            span.textContent = text.slice(0, -1);
                            span.style.marginLeft = (count * 8.8) + 'px';
                            lineDiv.appendChild(span);
    
                            text = text.slice(-1);
                            count = 0;
                        } else if (text.length == 1 && text == "'") {
                            one_quot = true;
                        } else if (text.length > 1 && text.slice(-1) == "'") {
                            one_quot = true;

                            var span = document.createElement("span");
                            if (keywords.includes(text.slice(0, -1))) {
                                span.classList.add("keyword");
                            }
                            span.textContent = text.slice(0, -1);
                            span.style.marginLeft = (count * 8.8) + 'px';
                            lineDiv.appendChild(span);
    
                            text = text.slice(-1);
                            count = 0;
                        }
                    }
                } else if (comment_multiline) {
                    text += lines[i][j];
                    if (text.length > 2 && text.slice(-2) == "*/") {
                        comment_multiline = false;
                        var span = document.createElement("span");
                        span.classList.add("comment");
                        span.style.marginLeft = (count * 8.8) + 'px';
                        span.textContent = text;
                        lineDiv.appendChild(span);
                        text = "";
                        count = 0;
                    }
                } else if (comment_singleline) {
                    text += lines[i][j];
                } else if (two_quot) {
                    text += lines[i][j];
                    if (text.length > 1 && text.slice(-1) == '"') {
                        two_quot = false;
                        var span = document.createElement("span");
                        span.classList.add("string");
                        span.style.marginLeft = (count * 8.8) + 'px';
                        span.textContent = text;
                        lineDiv.appendChild(span);
                        text = "";
                        count = 0;
                    }
                } else if (one_quot) {
                    text += lines[i][j];
                    if (text.length > 1 && text.slice(-1) == "'") {
                        one_quot = false;
                        var span = document.createElement("span");
                        span.classList.add("string");
                        span.style.marginLeft = (count * 8.8) + 'px';
                        span.textContent = text;
                        lineDiv.appendChild(span);
                        text = "";
                        count = 0;
                    }
                }
            }
            if (!comment_multiline && !comment_singleline && !one_quot && !two_quot) {
                if (text != "") {
                    var span = document.createElement("span");
                    if (keywords.includes(text)) {
                        span.classList.add("keyword");
                    }
                    span.textContent = text;
                    span.style.marginLeft = (count * 8.8) + 'px';
                    lineDiv.appendChild(span);
                } else if (count != 0) {
                    var span = document.createElement("span");
                    span.style.marginLeft = (count * 8.8) + 'px';
                    lineDiv.appendChild(span);
                }    
            } else if (comment_multiline) {
                var span = document.createElement("span");
                span.classList.add("comment");
                span.style.marginLeft = (count * 8.8) + 'px';
                span.textContent = text;
                lineDiv.appendChild(span);
            } else if (comment_singleline) {
                var span = document.createElement("span");
                span.classList.add("comment");
                span.style.marginLeft = (count * 8.8) + 'px';
                span.textContent = text;
                lineDiv.appendChild(span);
                comment_singleline = false;
            } else if (two_quot) {
                var span = document.createElement("span");
                span.classList.add("string");
                span.style.marginLeft = (count * 8.8) + 'px';
                span.textContent = text;
                lineDiv.appendChild(span);
            } else if (one_quot) {
                var span = document.createElement("span");
                span.classList.add("string");
                span.style.marginLeft = (count * 8.8) + 'px';
                span.textContent = text;
                lineDiv.appendChild(span);
            }
        } else {
            lineDiv.textContent = " ";
        }
        inputLinesDiv.appendChild(lineDiv);
    }

    inputLinesDiv.scrollTop = textarea.scrollTop;
    inputLinesDiv.scrollLeft = textarea.scrollLeft;
}

// function updateCursorPosition() {
//     var textarea = document.getElementById("input_textarea");
//     var cursorPosition = textarea.selectionStart;

//     var textBeforeCursor = textarea.value.substring(0, cursorPosition);
//     var textAfterCursor = textarea.value.substring(cursorPosition);
    
//     var lineNumber = (textBeforeCursor.match(/\n/g) || []).length + 1;

//     // Найдем позицию предыдущего перевода строки
//     var lastNewLineIndex = textBeforeCursor.lastIndexOf('\n');
//     var prevNewLineIndex = textBeforeCursor.lastIndexOf('\n', cursorPosition - 1);

//     // Найдем позицию следующего перевода строки
//     var nextNewLineIndex = textAfterCursor.indexOf('\n');
//     if (nextNewLineIndex !== -1) {
//         nextNewLineIndex += cursorPosition;
//     } else {
//         nextNewLineIndex = textarea.value.length;
//     }

//     // Если последний перевод строки не найден, значит курсор находится в первой строке
//     var positionInLine = cursorPosition - (prevNewLineIndex === -1 ? 0 : prevNewLineIndex + 1);

//     document.getElementById("mark_pos").textContent = ("Ln: " + lineNumber + " Col: " + positionInLine);
// }