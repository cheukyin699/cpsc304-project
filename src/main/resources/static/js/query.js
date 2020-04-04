$(document).ready(() => {
    $('#get-disease').on('click', () => {
        const name = $('#disease-name').val();
        $.get('/disease?symptom=' + name, populateDisease);
    });

    $('#by-ct-disease').on('click', () => {
        const name = $('#ct-disease-name').val();
        $.get('/disease?ct=' + name, populateDisease);
    });

    $('#link-disease-bt').on('click', () => {
        const dName = $('#link-disease-dname').val();
        const tName = $('#link-disease-tname').val();
        $.post(`/disease/link/${dName}/${tName}`, alert);
    });

    $('#ct-search-by-name').on('click', () => {
         const ctname = $('#ct-name').val();
         $.get('/clinicalTrial?ctname=' + ctname, populateClinicalTrial);
    });

    $('#ct-search-by-disease').on('click', () => {
        const name = $('#ct-search-by-dn').val();
        $.get('/clinicalTrial?dname=' + name, populateClinicalTrial);
    });
});

function populateDisease(data) {
    $('#disease-table tbody').remove();
    let rows = "";
    for (const row of data) {
        rows += `<tr><td>${row.name}</td><td>${row.prevalence}</td><td>${row.symptoms}</td>
                    <td><a class="btn btn-info" href="https://en.wikipedia.org/wiki/${row.name}">More Info</a></td>
                    <td><button class="btn btn-danger" onclick="deleteDisease('${row.name}')">Delete</button></td></tr>`;
    }
    rows = `<tbody>${rows}</tbody>`;
    $('#disease-table').append(rows);
}

function deleteDisease(name) {
    $.ajax({
        url:`/disease/${name}`,
        type: 'DELETE',
        success: () => {
            $('#get-disease').click();
        }
    });
}

function populateClinicalTrial(data){
    $('#clinical_trail_table tbody').remove();
        let rows = "";
        for (const row of data) {
            rows += `<tr><td>${row.trialName}</td>
                         <td>${row.type}</td>
                         <td>${row.numParticipants}</td>
                         <td>${row.isComplete}</td>
                         <td><button class="btn btn-danger" onclick="deleteClinicalTrial('${row.trialName}')">Delete</button></td></tr>`;
        }
        rows = `<tbody>${rows}</tbody>`;
        $('#clinical_trail_table').append(rows);
}

function deleteClinicalTrial(name) {
    $.ajax({
        url:`/clinicalTrial/${name}`,
        type: 'DELETE',
        success: () => {
            $('#ct-search-by-disease').click();
        }
    });
}
