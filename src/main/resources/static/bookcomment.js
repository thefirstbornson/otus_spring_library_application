function f_delete(id) {
    const formData = new FormData()
    formData.append('id', id)
    return fetch('/bookcomments/'+id, {
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