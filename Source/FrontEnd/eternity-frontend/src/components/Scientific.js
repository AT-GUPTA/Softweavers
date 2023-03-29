import './Scientific.css';
import {useEffect} from 'react';

function Scientific({execute}) {
    useEffect(() => {
        const display = document.getElementById("display");

        // Handle the function buttons
        const function_buttons = document.querySelectorAll(".function");
        function_buttons.forEach(function (button) {
            // Add a click event listener
            button.addEventListener("click", () => {
                // Append the button's value to the display
                let cursorStartPosition = display.selectionStart;
                let cursorEndPosition = display.selectionEnd;

                // Handle when we want to insert in a particular location
                if ((cursorEndPosition - cursorStartPosition) === 0){
                    let textBeforeCursorPosition = display.value.substring(0, cursorStartPosition)
                    let textAfterCursorPosition = display.value.substring(cursorStartPosition, display.value.length)
                    display.value = textBeforeCursorPosition + button.value + textAfterCursorPosition;
                }
                // Handle when we select a part of the display to replace with something else
                else {
                    let toReplace = display.value.substring(cursorStartPosition, cursorEndPosition);
                    display.value = display.value.replace(toReplace, button.value);
                }

                display.focus();
            })
        })

        // Handle the extra scientific buttons
        const other_buttons = document.querySelectorAll(".constant, .punctuation, .operator, .number");
        other_buttons.forEach(function (button) {
            // Add a click event listener
            button.addEventListener("click", () => {
                // Append the button's value to the display
                display.value += button.textContent;
                display.focus();
            })
        })

        const keyDownHandler = event => {
            if (event.key === 'Enter') {
                event.preventDefault();
                execute();
            }
        };
        document.addEventListener('keydown', keyDownHandler);

    }, []);

    function clearDisplay() {
        const display = document.getElementById("display");
        display.value = "";
        display.focus();
    }


    return (
        <div className="columns">
            <div className="first-column">
                <div id="calculator">
                    <label htmlFor="display"></label><textarea id="display" autoFocus></textarea>
                    <div id="keys">
                        <button className="function" id="sin" value="sin(">sinh</button>
                        <button className="function" id="arccos" value="arccos(">arccos</button>
                        <button className="function" id="mad" value="mad(">mad</button>
                        <button className="function" id="gamma" value="gamma(">gamma</button>
                        <button className="function" id="log" value="log(">log</button>
                        <button className="function" id="sqrt" value="pow(,1/2)">sqrt</button>
                        <button className="function" id="pow" value="pow(">x^y</button>
                        <button className="function" id="sd" value="sd(">sd</button>
                        <button className="operator">+</button>
                        <button className="operator">-</button>
                        <button className="operator">*</button>
                        <button className="operator">/</button>
                        <button className="number">7</button>
                        <button className="number">8</button>
                        <button className="number">9</button>
                        <button className="number">4</button>
                        <button className="number">5</button>
                        <button className="number">6</button>
                        <button className="number">1</button>
                        <button className="number">2</button>
                        <button className="number">3</button>
                        <button className="number">0</button>
                        <button className="punctuation" id="decimal">.</button>
                        <button id="clear" onClick={clearDisplay}>C</button>
                        <button className="constant" id="euler">e</button>
                        <button className="constant" id="pi">π</button>
                        <button className="function" id="exp" value="pow(e,)">e^x</button>
                        <button className="punctuation" id="comma">,</button>
                        <button className="punctuation" id="open">(</button>
                        <button className="punctuation" id="close">)</button>
                        <button id="equals" onClick={execute}>=</button>
                        <div id="errorMsgArea">
                            <textarea id="errorMsg" disabled></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Scientific;
