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

	$('#cross-ct-t').on('click', () => {
		const table = $('#cross-disease').val();
		const number = $('#cross-number').val();
		$.get('/disease?table='+ table +'&number='+ number, populateDisease);
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

	$('#find-patient-all-diseases').on('click', () => {
		$.get('/patient', populatePatients);
	});

	$('#display-oldest-by-physician').on('click', () => {
		$.get('/patient/oldest', populatePatients);
	});

	$('#select-patient').on('click', () => {
		const patientid = $('#id-for-patient').val();
		$.get('/patient?id=' + patientid, populatePatients);
		$.get(`/patient/count/${patientid}`, countRecords);
	});

	$('#delete-patient').on('click', () => {
		const patientid = $('#id-for-patient').val();
		deletePatient(patientid);
		countRecords(0);
	});

});




function populateDisease(data) {
	$('#disease-table tbody').remove();
	let rows = "";
	for (const row of data) {
		rows += `<tr><td>${row.name}</td><td>${row.prevalence}%</td><td>${row.symptoms}</td><td><a class="btn btn-info" href="https://en.wikipedia.org/wiki/${row.name}">More Info</a></td><td><button class="btn btn-danger" onclick="deleteDisease('${row.name}')">Delete</button></td></tr>`;
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

function countRecords(count) {
	$('#recordCount').val(count);
}

function populatePatients(data) {
	$('#patient-table tbody').remove();
	let rows = "";
	for (const row of data) {
		rows += `<tr><td>${row.id}</td><td>${row.patientName}</td><td>${row.familyHistory}</td>
		<td>${row.age}</td><td>${row.sex}</td><td>${row.physicianName}</td></tr>`;
	}
	rows = `<tbody>${rows}</tbody>`;
	$('#patient-table').append(rows);
}

function deletePatient(id) {
	$.ajax({
		url:`/patient/${id}`,
		type: 'DELETE',
		success: () => {
			$('#search-by-patient-id').click();
			$('#select-patient').click();
		}
	});
}
