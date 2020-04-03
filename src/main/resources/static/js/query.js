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
});

function populateDisease(data) {
    $('#disease-table tbody').remove();
    let rows = "";
    for (const row of data) {
        rows += `<tr><td>${row.name}</td><td>${row.prevalence}</td><td>${row.symptoms}</td><td><a class="btn btn-info" href="https://en.wikipedia.org/wiki/${row.name}">More Info</a></td><td><button class="btn btn-danger" onclick="deleteDisease('${row.name}')">Delete</button></td></tr>`;
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