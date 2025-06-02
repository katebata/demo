import {
    Button,
    Drawer, DrawerBody,
    DrawerCloseButton,
    DrawerContent, DrawerFooter,
    DrawerHeader,
    DrawerOverlay, Input,
    useDisclosure
} from "@chakra-ui/react";

import UpdateCustomerForm from "./UpdatecustomerForm.jsx";

const AddIcon = () => "+";
const CloseIcon = () => "x"

const UpdateCustomerDrawer = ({customerId,fetchCustomers,initialValues}) => {
    const { isOpen, onOpen, onClose } = useDisclosure()
    return <>
        <Button
            size="sm"
            colorScheme="gray"
            onClick={onOpen}
        >
            Update customer
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                <DrawerHeader>Update customer</DrawerHeader>

                <DrawerBody>
                    <UpdateCustomerForm
                        customerId = {customerId}
                        fetchCustomers = {fetchCustomers}
                        initialValues={initialValues}
                    />
                </DrawerBody>

                <DrawerFooter>
                    <Button leftIcon={<CloseIcon/>}
                            colorScheme={"teal"}
                            onClick={onClose}>
                        Close
                    </Button>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
    </>

}

export default UpdateCustomerDrawer;

