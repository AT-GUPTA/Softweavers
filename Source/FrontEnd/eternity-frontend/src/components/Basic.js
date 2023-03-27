import './Basic.css';


function Basic({clearFunction}) {
    return (
        <div className="first-column">
            <div id="calculator">
                <label htmlFor="display"></label><textarea id="display"></textarea>
                <div id="keys">
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
                    <button id="equals">=</button>
                </div>
            </div>
        </div>
    );
}

export default Basic;
