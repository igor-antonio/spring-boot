function getServerName() {

    let uri = "https://localhost:8443/sec/api/servername"
    uri = encodeURI(uri);

    fetch(uri)
        .then(res => res.json())
        .then(result => getServernameTable(result));
}

function getServernameTable(result) {

    let servernameTable = `
        <div class="table-responsive">
            <table class="table table-dark table-bordered table-hover">
                <tbody>
                    <thead>
                        <tr>
                            <th>ServernameA</th>
                            <th>ServernameB</th>
                            <th>ServernameC</th>
                            <th>ServernameD</th>
                            <th>ServernameE</th>
                        </tr>
                    </thead>
                `;

    for (let index = 0; index < result.length; index++) {
        servernameTable = servernameTable +
            `<tr>
                <td> ${result[index].serverName} </td>
                <td> ${result[index].serverName} </td>
                <td> ${result[index].serverName} </td>
                <td> ${result[index].serverName} </td>
                <td> ${result[index].serverName} </td>
            </tr>`;
    }

    servernameTable = servernameTable + `
                </tbody>
            </table>
        </div>`;

    document.getElementById("servernameTable").innerHTML = servernameTable;

}