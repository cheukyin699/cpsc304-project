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

    $('#t-search-by-name').on('click', ()=> {
       const tname = $('#tname').val();
       $.get('/treatment?tname=' + tname, populateTreatment);
    });

    $('#t-search-by-dn').on('click', ()=> {
      const dname = $('#treatment-dname').val();
      $.get('/treatment?dname=' +dname, populateTreatment);
    });

    $('#t-cross').on('click', () => {
      const table = $('#cross-field').val();
      $.get('/treatment?table=' + table, populateTable);
    })


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

function populateTreatment(data) {
  $('#treatment-table tbody').remove();
  let rows = "";
  for(const row of data){
    rows += `<tr><td>${row.treatmentName}</td>
                 <td>${row.efficiency}</td>
                 <td>${row.equipment}</td>
                 <td>${row.risks}</td>
                 <td><a class="btn btn-info" href="https://en.wikipedia.org/wiki/${row.treatmentName}"> More Info</a></td></tr>`;
  }
  rows = `<tbody>${rows}</tbody>`;
  $('#treatment-table').append(rows);
}

function populateTable(data) {
  $('#treatment-table tbody').remove();
  $('#treatment-table thead').remove();
  let heads = `<thead><tr>
      <th scope="col">Treatment Name</th>
      <th scope="col">Disease/Clnical Trial</th>
      <th scope="col">Symptoms/Type</th></thead>`;
  $('#treatment-table').append(heads);
  let rows = "";

  rows = `<tbody>${rows}</tbody>`;
  $('#treatment-table').append(rows);
}
