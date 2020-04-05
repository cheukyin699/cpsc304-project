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

    $('#search-by-patient-id').on('click', () => {
        const patientid = $('#patient-id').val();
        $.get('/medical_record?patientid=' + patientid, populateMedicalRecord);
    });

    $('#edit-medications').on('click', () => {
        const patientid = $('#patient-id').val();
        const medications = $('#new-medication').val();
        const start_date =  $('#start_date').val();
        $.post(`/medical_record/edit/${patientid}/${medications}/${start_date}`, refresh);
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

function populateMedicalRecord(data) {
    $('#medical_record_table tbody').remove();
    let rows = "";
    for (const row of data) {
        rows += `<tr><td>${row.patientId}</td><td>${row.startDate}</td><td>${row.endDate}</td>
        <td>${row.disease}</td><td>${row.implants_sur}</td><td>${row.allergies}</td><td>${row.med}</td></tr>`;
    }
    rows = `<tbody>${rows}</tbody>`;
    $('#medical_record_table').append(rows);
}

function refresh() {
    $('#search-by-patient-id').click();
}


