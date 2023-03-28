import './Calculator.css';
import {useState} from 'react';

import Settings from "./components/Settings";
import Scientific from "./components/Scientific";
import History from "./components/History";

function Calculator() {
    const [isChecked, setIsChecked] = useState(false);
    const [histories, setHistories] = useState([]);
    const handleCheckboxChange = (event) => {
        setIsChecked(event.target.checked);
    };

    const handleChangeHistory = () => {
        const display = document.getElementById("display");
        setHistories(histories => ([...histories,
            <option key={histories.length + 1} value={display.value}>{display.value}</option>]))
    };



    return (
        <div className="columns">
            <Scientific execute={handleChangeHistory}/>
            <div className="second-column">
                <Settings handleCheckbox={handleCheckboxChange}/>
                <History histories={histories}/>
            </div>

        </div>
    );
}

export default Calculator;
{/*{isChecked ? (*/}
{/*    <Basic clearFunction={clearDisplay}/>*/}
{/*) : (*/}
{/*    <Scientific clearFunction={clearDisplay} execute={handleChangeHistory}/>*/}
{/*)}*/}