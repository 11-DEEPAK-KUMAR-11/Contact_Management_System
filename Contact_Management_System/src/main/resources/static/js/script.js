console.log("This is contact management system");

const toggleSidebar = () => {
if($(".sidebar").is(":visible"))
{
    //if true -> close the tab
    $(".sidebar").css("display","none");
    $(".content").css("margin-left","0%");

} else{
    //if flase -> open the tab (show the tab)

    $(".sidebar").css("display","block");
    $(".content").css("margin-left","20%");
}

};

const search =() => {

    let query=$("#search-input").val();

    if(query=="")
    {
        $(".search-result").hide();
    }

    else{
        // console.log(query);

        //Sending request to server
        let url=`http://localhost:8889/search/${query}`;

        fetch(url).then((response) =>{

            return response.json();
        }).then( (data) =>{
 
            // console.log(data);
            let text=`<div class='list-group'>`;
            
            data.forEach((contact) => {
                
                text+=`<a href='/user/${contact.id}/contact' class='list-group-item list-group-item-action'>${contact.name}</a>`;

            });


            text+=`</div>`;
 
            $(".search-result").html(text);
            $(".search-result").show();
        })


        
    }

}