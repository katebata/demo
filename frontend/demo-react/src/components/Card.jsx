'use client'

import {
    Heading,
    Avatar,
    Box,
    Center,
    Image,
    Flex,
    Text,
    Stack,
    Button,
    useColorModeValue,
    Badge
} from '@chakra-ui/react'

import DeleteCustomerAlertDialog from './DeleteCustomerAlertDialog.jsx';
import UpdateCustomerForm from "./UpdatecustomerForm.jsx";
import UpdateCustomerDrawer from "./UpdateCustomerDrawer.jsx";

export default function CardWithImage({id,name,email,age,gender,fetchCustomers}) {

    const genderFolder = gender === "MALE" ? "men" : "women";
    const avatarSrc = `https://randomuser.me/api/portraits/${genderFolder}/${id}.jpg`;

    return (
        <Center py={6}>
            <Box
                maxW={'300px'}
                minW={'300px'}
                w={'full'}
                m={2}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'lg'}
                rounded={'md'}
                overflow={'hidden'}>
                <Image
                    h={'120px'}
                    w={'full'}
                    src={
                        'https://images.unsplash.com/photo-1612865547334-09cb8cb455da?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80'
                    }
                    objectFit="cover"
                    alt="#"
                />
                <Flex justify={'center'} mt={-12}>
                    <Avatar
                        size={'xl'}
                        src={avatarSrc}
                        css={{
                            border: '2px solid white',
                        }}
                    />
                </Flex>

                <Box p={6}>
                    <Stack spacing={2} align={'center'} mb={5}>
                        <Badge variant="solid" colorScheme="blue">ID {id}</Badge>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {name}
                        </Heading>
                        <Text color={'gray.500'}>{email}</Text>
                        <Text color={'gray.500'}>Age {age} | {gender}</Text>
                    </Stack>
                    <Stack direction={'row'} justify={'center'}  spacing={6}>
                        <UpdateCustomerDrawer
                            customerId={id}
                            fetchCustomers={fetchCustomers}
                            initialValues={{name,email,age,gender}}
                        />

                        <DeleteCustomerAlertDialog
                            customerId = {id}
                            customerName = {name}
                            fetchCustomers={fetchCustomers}
                        />
                    </Stack>
                </Box>
            </Box>
        </Center>
    )
}
