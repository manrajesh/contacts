import React, { useEffect, useState } from "react";
import Box from '@mui/material/Box';
import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';
import Pagination from '@mui/material/Pagination';
import ImageListItemBar from '@mui/material/ImageListItemBar';
import IconButton from '@mui/material/IconButton';
import LoadingSpinner from "./LoadingSpinner";
import PersonIcon from '@mui/icons-material/Person';
import TextField from '@mui/material/TextField';

const Contacts = () => {
    const [contacts, setContacts] = React.useState(null);
    const [pages, setPages] = React.useState(0);
    const [pagination, setPagination] = useState(0);
    const [searchTerm, setSearchTerm] = React.useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        loadAllUsersByPage(pagination);
    }, []);

    const loadAllUsersByPage = (page) => {
        setIsLoading(true);
        fetch(`/api/contacts?page=${page}&size=18`)
            .then(results => results.json())
            .then(
                (response) => {
                    setContacts(response.content);
                    setPages(response.totalPages);
                    setIsLoading(false);
                })
            .catch(() => {
                setErrorMessage("Unable to fetch contacts list");
                setIsLoading(false);
            });
    };

    const searchByName = (searchText, page) => {
        setSearchTerm(searchText);
        if (searchText !== '') {
            setIsLoading(true);
            fetch(`/api/contacts/search?searchTerm=${searchText}&page=${page}&size=18`)
                .then(results => results.json())
                .then(
                    (response) => {
                        setContacts(response.content);
                        setPages(response.totalPages);
                        setPagination(page);
                        setIsLoading(false);
                    })
                .catch(() => {
                    setErrorMessage("Unable to fetch contact list on search");
                    setIsLoading(false);
                });
        } else {
            loadAllUsersByPage(0);
            setPagination(0);
        }
    };

    const onPageChange = (page) => {
        if (searchTerm !== '') {
            const debounceHandler = setTimeout(() => {
                searchByName(searchTerm, page - 1);
            }, 3000)

            return () => {
                clearTimeout(debounceHandler);
            };
        } else {
            loadAllUsersByPage(page - 1);
        }
        setPagination(page - 1);
    }


    return (
        <div>
            {contacts ?
                <Box sx={{ width: 1300, height: 900 }}>
                    <TextField
                        size="large"
                        margin="dense"
                        variant="outlined"
                        label="Search contacts by name"
                        id="searchName"
                        sx={{ mr: 30, ml: 30 }}
                        onChange={(e) => searchByName(e.target.value, 0)}
                    />
                    &ensp;
                    <ImageList
                        variant="standard"
                        cols={6}
                        rows={4}
                        gap={20}
                    >
                        {contacts.map((item) => (
                            <ImageListItem key={item.id}>
                                <img
                                    src={item.imageUrl}
                                    alt={item.name}
                                    loading="lazy"
                                    width="250" 
                                    height="250"
                                />
                                <ImageListItemBar
                                    position="below"
                                    title={item.name}
                                    actionIcon={
                                        <IconButton
                                            sx={{ color: 'green' }}
                                            aria-label={`star ${item.name}`}
                                        >
                                            <PersonIcon />
                                        </IconButton>
                                    }
                                    actionPosition="left"
                                />
                            </ImageListItem>
                        ))}
                    </ImageList>
                    &ensp;
                    <Pagination
                        count={pages}
                        disabled={isLoading}
                        color="primary"
                        page={pagination + 1}
                        size="large"
                        sx={{ mr: 30, ml: 30 }}
                        onChange={(e, page) => onPageChange(page)}
                    />
                </Box>
                : <LoadingSpinner />}
            {errorMessage && <div className="error">{errorMessage}</div>}
        </div>
    );
}

export default Contacts;