import './History.css';
import Dropdown from 'react-bootstrap/Dropdown';
import Button from 'react-bootstrap/Button';

function History({histories}) {

    const updateDisplay = (event) => {
        const display = document.getElementById("display");
        if (event.target.text !== undefined)
            if (event.target.attributes.value !== undefined)
                display.value = event.target.text + "=" + event.target.attributes.value.value;
            else
                display.value = event.target.text;
        display.focus()
    }

    const exportHistory = () => {
        const historyText = histories.map((history) => `${history.label}=${history.value}`).join('\n');
        const element = document.createElement("a");
        const file = new Blob([historyText], {type: 'text/plain'});
        element.href = URL.createObjectURL(file);
        element.download = "history.txt";
        document.body.appendChild(element);
        element.click();
    }

    return (
        <div className="history-container">
            <div className="title">
                History:
                </div><div>
                    
                <Button variant="secondary" className="download-button" onClick={exportHistory}>
                    Export History <i className="fa fa-download icon"></i>
                </Button>
            </div>
            <div>
                <Dropdown.Menu show id="history" className="history_input" onChange={(e) => updateDisplay(e)}
                               onClick={(e) => updateDisplay(e)}>
                    {histories.map((history) =>
                        <Dropdown.Item className={"item"} key={histories.indexOf(history)}
                                       value={history.value}>{history.label}</Dropdown.Item>
                    )}
                </Dropdown.Menu>
                
            </div>
            
        </div>
    );
}

export default History;
