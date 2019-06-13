function f_delete(id) {
    const formData = new FormData()
    formData.append('id', id)
    return fetch('/authors/'+id, {
        method: 'delete',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body:  JSON.stringify({id: id})
    }).then(function(response) {
        window.location.reload(false);
        return response.json();});

}

function f_edit(id) {
    localStorage.setItem("author_id", id);
}

function f_save() {
   var id =  document.getElementById("id-input").value==""?"0":document.getElementById("id-input").value;
   var fname = !document.getElementById("firstname-edit")?document.getElementById("firstname-new").value:document.getElementById("firstname-edit").value;
   var lname = !document.getElementById("lastname-edit")?document.getElementById("lastname-new").value:document.getElementById("lastname-edit").value;
    return fetch('/authors', {
        method: 'post',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body:  JSON.stringify({id:parseInt(id),fname:fname.toString(), lname: lname.toString() })
    }).then(function(response) {
        window.location.replace("authors.html");
        return response.json();});
}