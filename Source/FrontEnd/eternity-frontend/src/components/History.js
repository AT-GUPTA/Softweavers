import './History.css';


function History({histories}) {

    const updateDisplay = (event) => {
        const display = document.getElementById("display");
        display.value = event.target.value
    }

    return (
        <div>
            <div className="history">
                History:
            </div>
            <div>
                <select className="history_input" size={20} onChange={(e) => updateDisplay(e)}
                        onClick={(e) => updateDisplay(e)}>
                    {histories.map((history) => history)}
                </select>
            </div>
        </div>
    );
}

export default History;
