import './Settings.css';


function Settings() {
    function createSelectItems() {
        let items = [];
        for (let i = 1; i <= 12; i++) {
            items.push(<option key={i} value={i}>{i}</option>);
        }
        return items;
    }

    return (
        <div>
            <div className="header">
                Settings:
            </div>
            <div>
                <label>
                    <input type="checkbox" className="switch"/>
                    Scientific notation
                </label>
            </div>
            <div>
                <div className="header">
                    Precision adjustment:
                </div>
                <label className="flushOption">
                    Precision of:
                    <select className="dropdown">
                        {createSelectItems().map((precision) =>
                            precision)}
                    </select>
                </label>

            </div>
        </div>
    );
}

export default Settings;
