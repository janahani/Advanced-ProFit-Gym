function getclasses(){
let option=document.getElementById("select-coaches").value;
let postData={'id':option};
fetch('../Controllers/coachapi.php', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify(postData),
}) .then(response => response.json())
.then(data => {
    let val=data['data'];
    console.log('POST Response: ', val['key']);
})
.catch(error => console.error('Error:', error));

}