function f_delete(id) {
    const formData = new FormData()
    formData.append('id', id)
    return fetch('/genres/'+id, {
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
    localStorage.setItem("genre_id", id);
}

function f_save() {
   var id =  document.getElementById("id-input").value==""?"0":document.getElementById("id-input").value;
   var genrename = !document.getElementById("genrename-edit")?document.getElementById("genrename-new").value:document.getElementById("genrename-edit").value;
    return fetch('/genres', {
        method: 'post',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body:  JSON.stringify({id:parseInt(id),genreName:genrename})
    }).then(function(response) {
        window.location.replace("genres.html");
        return response.json();});
}