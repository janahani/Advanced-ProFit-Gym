function save(date){
let form =document.getElementsByClassName("checkboxes");
let hiddens=document.getElementsByClassName("hiddens");
let arr={};

for(let i=0;i<hiddens.length;i++){
    let num=hiddens[i].value;
    let str=`emp${num}`;
   if(findinform(hiddens[i].value,form)){
    arr[str]=1;
 
   }else{
    arr[str]=0;
  
   }

}

arr.date=date;

    fetch("../Controllers/attendance.php", {
        method: "POST",
        headers:{"Content-Type": "application/json",},
        body: JSON.stringify(arr)
    }).then(response => response.json())
    .then(data => {
       
        document.getElementById("modalmsg").style.display="block";
    })
    .catch(error => {
        console.error("Error:", error);
    });

}
function findinform(id,arr){
    for(let i=0;i<arr.length;i++){
        if(arr[i].value===id&&arr[i].checked){
            return true;
        }
    }
    return false;
}
function closemsg(){
document.getElementById("modalmsg").style.display="none";
}