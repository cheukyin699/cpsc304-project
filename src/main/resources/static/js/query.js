$(document).ready(() => {
    $('#get-disease').on('click', () => {
        const name = $('#disease-name').val();
        $.get('/disease?symptom=' + name, (data) => {
            // Data is a list of diseases
            $('#disease-table tbody').remove();
            let rows = "";
            for (const row of data) {
                rows += `<tr><td>${row.name}</td><td>${row.prevalence}</td><td>${row.symptoms}</td><td><a class="btn btn-info" href="https://en.wikipedia.org/wiki/${row.name}">More Info</a></td><td>No edit</td><td><button class="btn btn-danger" onclick="deleteDisease('${row.name}')">Delete</button></td></tr>`;
            }
            rows = `<tbody>${rows}</tbody>`;
            $('#disease-table').append(rows);
        });
    });
});

function deleteDisease(name) {
    $.ajax({
        url:`/disease/${name}`,
        type: 'DELETE',
        success: () => {
            $('#get-disease').click();
        }
    });
}