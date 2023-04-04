import { useCallback } from 'react';
import './Scientific.css';
import {useEffect, useRef} from 'react';

function Scientific({execute}) {
  const displayRef = useRef(null);

  const onClickFunction = useCallback((button) => {
    const display = displayRef.current;

    // Append the button's value to the display
    let cursorStartPosition = display.selectionStart;
    let cursorEndPosition = display.selectionEnd;

    // Handle when we want to insert in a particular location
    if ((cursorEndPosition - cursorStartPosition) === 0) {
      let textBeforeCursorPosition = display.value.substring(0, cursorStartPosition)
      let textAfterCursorPosition = display.value.substring(cursorStartPosition, display.value.length)
      display.value = textBeforeCursorPosition + button.value + textAfterCursorPosition;
    }
    // Handle when we select a part of the display to replace with something else
    else {
      let toReplace = display.value.substring(cursorStartPosition, cursorEndPosition);
      display.value = display.value.replace(toReplace, button.value);
    }

    // Handle leaving the cursor in the correct location for inserting variable
    if (button.id === "abx")
      display.selectionEnd = display.selectionStart - 4;

    display.focus();
  }, []);


  const onOtherButtonClick = useCallback ((button) => {
    const display = displayRef.current;

    // Append the button's value to the display
    display.value += button.textContent;
    display.focus();
  }, [])

  useEffect(() => {
    const function_buttons = document.querySelectorAll(".function");
    function_buttons.forEach(function (button) {
      button.addEventListener("click", () => onClickFunction(button));
    });

    const other_buttons = document.querySelectorAll(".constant, .punctuation, .operator, .number");
    other_buttons.forEach(function (button) {
      button.addEventListener("click", () => onOtherButtonClick(button));
    });

    const keyDownHandler = event => {
      if (event.key === 'Enter') {
        event.preventDefault();
        execute();
      }
    };

    document.addEventListener('keydown', keyDownHandler);
    return () => {
      function_buttons.forEach(function (button) {
        button.removeEventListener("click", () => onClickFunction);
      });
      other_buttons.forEach(function (button) {
        button.removeEventListener("click", () => onOtherButtonClick);
      });
      document.removeEventListener('keydown', keyDownHandler);
    };
  }, []);

  function clearDisplay() {
    const display = displayRef.current;
    display.value = "";
    display.focus();
  }

  function submitPrep() {
    const display = displayRef.current;
    if (display.value.includes('=')) {
      display.value = display.value.split('=')[0];
    }
  }

  return (
    <div id="calculator">
      <label htmlFor="display"></label><textarea ref={displayRef} id="display" onChange={submitPrep} autoFocus></textarea>
      <div id="keys">
                <button className="function" id="sinh" value="sinh(">sinh</button>
                <button className="function" id="arccos" value="arccos(">arccos</button>
                <button className="function" id="mad" value="mad(">mad</button>
                <button className="function" id="gamma" value="gamma(">gamma</button>
                <button className="function" id="log" value="log(">log</button>
                <button className="function" id="sqrt" value="pow(, 1/2)">sqrt</button>
                <button className="function" id="pow" value="pow(">x<sup>y</sup></button>
                <button className="function" id="sd" value="sd(">sd</button>
                <button className="function" id="abx" value="abx(, ,)">ab<sup>x</sup></button>
                <button className="function" id="exp" value="pow(e,)">e<sup>x</sup></button>
                <button className="constant" id="euler">e</button>
                <button className="constant" id="pi">π</button>
                <button className="number">7</button>
                <button className="number">8</button>
                <button className="number">9</button>
                <button className="operator">+</button>
                <button className="number">4</button>
                <button className="number">5</button>
                <button className="number">6</button>
                <button className="operator">-</button>
                <button className="number">1</button>
                <button className="number">2</button>
                <button className="number">3</button>
                <button className="operator">*</button>
                <button className="number">0</button>
                <button className="punctuation" id="decimal">.</button>
                <button id="clear" onClick={clearDisplay}>C</button>
                <button className="operator">/</button>
                <button className="punctuation" id="open">(</button>
                <button className="punctuation" id="comma">,</button>
                <button className="punctuation" id="close">)</button>
                <button id="equals" onClick={execute}>=</button>
            </div>
        </div>
    );
}

export default Scientific;
