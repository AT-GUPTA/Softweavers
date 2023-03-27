import './Settings.css';


function Settings({handleCheckbox}) {
    function createSelectItems() {
        let items = [];
        for (let i = 1; i <= 12; i++) {
            items.push(<option key={i} value={i}>{"Accuracy of ".concat(i)}</option>);
        }
        return items;
    }

    return (
        <div>
            <div className="header">
                Settings
            </div>
            <div>
                <label>
                    <input type="checkbox" className="switch" onChange={handleCheckbox}/>
                    Basic/Scientific
                </label>
            </div>
            <div>
                <label>
                    Accuracy adjustment:
                    <select className="dropdown">
                        {createSelectItems().map((accuracy) =>
                            accuracy)}
                    </select>
                </label>

            </div>
        </div>
    );
}

export default Settings;
