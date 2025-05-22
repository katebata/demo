import { Spinner, Text, Wrap, WrapItem } from '@chakra-ui/react';
import {useEffect, useState} from "react";
import SidebarWithHeader from "./components/shared/SideBar.jsx";
import {getCustomers} from "./services/client.js";
import CardWithImage from "./components/Card.jsx";


const App = () => {

    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        getCustomers().then(res => {
            setCustomers(res.data);
        }).catch(err => console.log(err))
            .finally(() =>{
                setLoading(false);
            })
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

    if(customers.length <= 0){
        return (
            <SidebarWithHeader>
                <Text>No customers available</Text>
            </SidebarWithHeader>)
    }
    return(
        <SidebarWithHeader>
            <Wrap justify={"center"} spacing={"30px"}>
                {customers.map((customer, index) => (
                    <WrapItem key={index}>
                        <CardWithImage {...customer}/>
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>
    )
}

export default App;