$(document).ready(() => {
	$('#ct-search-by-name').on('click', () => {
		const ctname = $('#ct-name').val();
		$.get('/clinicalTrial?ctname=' + ctname, populateClinicalTrial);
	});

	$('#ct-search-by-disease').on('click', () => {
		const name = $('#ct-search-by-dn').val();
		$.get('/clinicalTrial?dname=' + name, populateClinicalTrial);
	});



	$('#ct-filter-by').on('click', () => {
		const field = $('#ct-field').val();
		$.get('/clinicalTrial?field=' + field, populateJustName)
	});
});



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
