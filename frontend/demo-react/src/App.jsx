import { Spinner, Text, Wrap, WrapItem } from '@chakra-ui/react';
import {useEffect, useState} from "react";
import SidebarWithHeader from "./components/shared/SideBar.jsx";
import {getCustomers} from "./services/client.js";
import CardWithImage from "./components/Card.jsx";
import CreateCustomerDrawer from "./components/CreateCustomerDrawer.jsx";
import {errorNotification} from "./services/notification.js";


const App = () => {
    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [err,setError] = useState("")

    const fetchCustomers = () => {
        setLoading(true);
        getCustomers().then(res => {
            const payload = res.data;

            // 1) If res.data is already an array:
            if (Array.isArray(payload)) {
                setCustomers(payload);
            }
            // 2) If the server wraps it as { data: [ … ] }:
            else if (Array.isArray(payload.data)) {
                setCustomers(payload.data);
            }
            // 3) If the server wraps it as { customers: [ … ] }:
            else if (Array.isArray(payload.customers)) {
                setCustomers(payload.customers);
            }
            // 4) Fallback:
            else {
                console.error("Unexpected response shape:", payload);
                setCustomers([]); // or you might want to set an error instead
            }
        }).catch(err => {
            errorNotification(
                err.code,
                err.response.data.message
            )
            }
        ).finally(() =>{
                setLoading(false);
            })
    }

    useEffect(() => {
        fetchCustomers();
    }, []);


    if(loading){
        return (
            <SidebarWithHeader>
                <Spinner
                    color="blue.500"
                    borderWidth="3px"
                    size="xl"
                />
            </SidebarWithHeader>)
    }

    if(err){
        return (
            <SidebarWithHeader>
                <CreateCustomerDrawer
                    fetchCustomers={fetchCustomers}
                />
                <Text mt={5}>Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }

    if(customers.length <= 0){
        return (
            <SidebarWithHeader>
                <CreateCustomerDrawer
                    fetchCustomers = {fetchCustomers}
                />
                <Text mt={"5px"}>No customers available</Text>
            </SidebarWithHeader>)
    }
    return(
        <SidebarWithHeader>
            <CreateCustomerDrawer
                fetchCustomers = {fetchCustomers}
            >
            </CreateCustomerDrawer>
            <Wrap justify={"center"} spacing={"30px"}>
                {customers.map((customer, index) => (
                    <WrapItem key={index}>
                        <CardWithImage
                            {...customer}
                            fetchCustomers = {fetchCustomers}
                        />
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>
    )
}

export default App;