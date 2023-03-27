import './Scientific.css';
import {useEffect} from 'react';

function Scientific({clearFunction, execute}) {
    useEffect(() => {
        const display = document.getElementById("display");

        // Hnadle the function buttons
        const function_buttons = Array.from(document.querySelectorAll(".function"));
        function_buttons.forEach(function (button) {
            // Add a click event listener
            button.addEventListener("click", () => {
                // Append the button's value to the display
                display.value += button.textContent + "(";
            })
        })
    }, []);


    return (
        <div className="columns">
            <div className="first-column">
                <div id="calculator">
                    <label htmlFor="display"></label><textarea id="display"></textarea>
                    <div id="keys">
                        <button className="function" id="sin">sinh</button>
                        <button className="function" id="arcos">arcos</button>
                        <button className="function" id="mad">mad</button>
                        <button className="function" id="gamma">gamma</button>
                        <button className="function" id="log">log</button>
                        <button className="function" id="sqrt">sqrt</button>
                        <button className="function" id="inv">1/x</button>
                        <button className="function" id="pow">x^y</button>
                        <button className="function" id="sd">sd</button>
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
                        <button id="decimal">.</button>
                        <button id="clear" onClick={clearFunction}>C</button>
                        <button id="euler">e</button>
                        <button id="pi">π</button>
                        <button id="exp">e^x</button>
                        <button id="comma">,</button>
                        <button id="open">(</button>
                        <button id="close">)</button>
                        <button id="equals" onClick={execute}>=</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Scientific;
