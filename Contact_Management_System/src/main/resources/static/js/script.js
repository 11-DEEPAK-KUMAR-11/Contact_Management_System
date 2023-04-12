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

//First request to sever to create order

const paymentStart = () =>{

    console.log("Payment started..");
    let amount=$("#payment_field").val();

    console.log(amount);

    if(amount=="" || amount==null)
    {
        // alert("Amount is required..!");
        swal("Failed !", "Amount is required..!", "error", {
            button: "Done !",
          });
        return;
    }

    //we will use Ajax to send request to server to create order - jquery Ajax full version(minified)

    $.ajax(
       {
        "url":'/user/create_order',
        "data":JSON.stringify({amount:amount,info:'order_request'}),
        "contentType":'application/json',
        "type":'POST',
        "dataType":'json',
        "success":function(response){
            
            //invoke when success
            console.log("open payment form")
            console.log(response);
            if(response.status=="created")
            {
	           
	           console.log("open payment form");
                //open payment form
                let options={
                    key:"rzp_test_O8bLNtDGlCbGoh",
                    amount:response.amount,
                    currency:"INR",
                    name:"Contact Management System",
                    description:"Donation",
                    image:"https://w7.pngwing.com/pngs/151/112/png-transparent-business-company-management-plan-industry-contact-us-company-service-people-thumbnail.png",
                    order_id:response.id,
                    handler:function(response){
                        console.log(response.razorpay_payment_id);
                        console.log(response.razorpay_order_id);
                        console.log(response.razorpay_signature);
                        console.log("Payment successful..!");
                        // alert("Congrats ! Payment successful..!");

                        swal("Congrats !", "Payment successful..!", "success", {
                            button: "Done !",
                          });
                    },

                    prefill: {
                        "name": "",
                        "email": "",
                        "contact": ""
                    },
                    notes: {
                        "address": "Contact Management System Corporate Office"
                    },
                    theme: {
                        "color": "#3399cc"
                    }
                };

                var rzp1 = new Razorpay(options);
                rzp1.on('payment.failed', function (response){
                    console.log(response.error.code);
                    console.log(response.error.description);
                    console.log(response.error.source);
                    console.log(response.error.step);
                    console.log(response.error.reason);
                    console.log(response.error.metadata.order_id);
                    console.log(response.error.metadata.payment_id);

                    // alert("Oops Payment failed..!");
                    swal("Failed !", "Oops Payment failed..!", "error", {
                        button: "Done !",
                      });
                });

                
                 document.getElementById("clicked").onclick = function(e){
                     rzp1.open();
                    
                 }
            }
        },
        "error":function(error){

            //invoke when error occurred
            console.log(error);
            alert("Something went wrong..!!")
        }

       }

    )

}