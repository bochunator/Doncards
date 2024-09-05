import React, { useEffect, useState } from 'react'
import { Button, Form } from 'react-bootstrap'
import { useDispatch, useSelector } from 'react-redux'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faAngleDown, faAngleUp, faAnglesDown, faAnglesUp, faClone, faTimes } from '@fortawesome/free-solid-svg-icons'

import './CreateDeck.css'
import { CreateDeckData, defaultCreateCard, defaultCreateDeckData } from '../../types/deckTypes'
import { AppDispatch, RootState } from '../../redux/Store'
import { createDeck } from '../../redux/Slices/DeckSlice'
import { useNavigate } from '../../hooks/useNavigate'


const CreateDeck: React.FC = () => {
    const [createDeckData, setCreateDeckData] = useState<CreateDeckData>(defaultCreateDeckData)
    const { createDeckPayload } = createDeckData
    const { name, description, cardDTOs } = createDeckPayload
    const dispatch: AppDispatch = useDispatch()
    const { jwt } = useSelector((state: RootState) => state.auth)
    const { createdDeck } = useSelector((state: RootState) => state.deck)
    const navigate = useNavigate()

    useEffect(() => {
        if (createdDeck) {
            navigate("/Doncards/learning/${createdDeck.deckId}")
        }
    }, [createdDeck])

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        console.log('name =', name)
        if (name in createDeckPayload) {
            setCreateDeckData(prevState => ({
                ...prevState,
                createDeckPayload: {
                    ...prevState.createDeckPayload,
                    [name]: value
                }
            }))
        } else {
            setCreateDeckData(prevState => ({
                ...prevState,
                [name]: value
            }))
        }
        console
            .log('createDeckData', createDeckData)
    }
    const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        dispatch(createDeck({ body: createDeckPayload, token: jwt! }))
        console.log('onSubmit')
    }

    const onCardChange = (index: number, e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target
        const updatedCardDTOs = cardDTOs.map((card, i) =>
            i === index ? { ...card, [name]: value } : card
        )
        setCreateDeckData(prevState => ({
            ...prevState,
            createDeckPayload: {
                ...prevState.createDeckPayload,
                cardDTOs: updatedCardDTOs
            }
        }))
        console.log('createDeckData', createDeckData)
    }

    const cloneNewCard = (index: number) => {
        const cardToClone = { ...cardDTOs[index] }
        setCreateDeckData(prevState => ({
            ...prevState,
            createDeckPayload: {
                ...prevState.createDeckPayload,
                cardDTOs: [...cardDTOs, cardToClone]
            }
        }))
        console.log('createDeckData', createDeckData)
    }

    const removeCard = (index: number) => {
        setCreateDeckData(prevState => ({
            ...prevState,
            createDeckPayload: {
                ...prevState.createDeckPayload,
                cardDTOs: prevState.createDeckPayload.cardDTOs.filter((_, i) => i !== index)
            }
        }))
        console.log('createDeckData', createDeckData)
    }

    const moveCardUp = (index: number) => {
        if (index <= 0) return
        setCreateDeckData(prevState => {
            const updatedCards = [...prevState.createDeckPayload.cardDTOs]
            const [movedCard] = updatedCards.splice(index, 1)
            updatedCards.splice(index - 1, 0, movedCard)
            return {
                ...prevState,
                createDeckPayload: {
                    ...prevState.createDeckPayload,
                    cardDTOs: updatedCards
                }
            };
        });
    }

    const moveCardDown = (index: number) => {
        if (index >= cardDTOs.length - 1) return
        setCreateDeckData(prevState => {
            const updatedCards = [...prevState.createDeckPayload.cardDTOs]
            const [movedCard] = updatedCards.splice(index, 1)
            updatedCards.splice(index + 1, 0, movedCard)
            return {
                ...prevState,
                createDeckPayload: {
                    ...prevState.createDeckPayload,
                    cardDTOs: updatedCards
                }
            }
        })
    }

    const moveCardToTop = (index: number) => {
        if (index <= 0) return
        setCreateDeckData(prevState => {
            const updatedCards = [...prevState.createDeckPayload.cardDTOs]
            const [movedCard] = updatedCards.splice(index, 1)
            updatedCards.unshift(movedCard)
            return {
                ...prevState,
                createDeckPayload: {
                    ...prevState.createDeckPayload,
                    cardDTOs: updatedCards
                }
            }
        })
    }

    const moveCardToBottom = (index: number) => {
        if (index >= cardDTOs.length - 1) return
        setCreateDeckData(prevState => {
            const updatedCards = [...prevState.createDeckPayload.cardDTOs]
            const [movedCard] = updatedCards.splice(index, 1)
            updatedCards.push(movedCard)
            return {
                ...prevState,
                createDeckPayload: {
                    ...prevState.createDeckPayload,
                    cardDTOs: updatedCards
                }
            }
        })
    }

    const cards = () => {
        return cardDTOs.map(({ term, translation }, index) => (
            <div className='new-card' key={index}>
                {index + 1}
                <Form.Control
                    className='term'
                    placeholder='term'
                    name='term'
                    value={term}
                    onChange={(e) => onCardChange(index, e)}
                />
                <Form.Control
                    className='translation'
                    placeholder='translation'
                    name='translation'
                    value={translation}
                    onChange={(e) => onCardChange(index, e)}
                />
                <FontAwesomeIcon
                    icon={faClone}
                    className='card-white-icon'
                    onClick={() => cloneNewCard(index)}
                />
                <FontAwesomeIcon
                    icon={faTimes}
                    className='card-red-icon'
                    onClick={() => removeCard(index)}
                />
                {index === 0 ? <></> : <FontAwesomeIcon
                    icon={faAngleUp}
                    className='card-white-icon'
                    onClick={() => moveCardUp(index)}
                />}
                {index === cardDTOs.length - 1 ? <></> : <FontAwesomeIcon
                    icon={faAngleDown}
                    className='card-white-icon'
                    onClick={() => moveCardDown(index)}
                />}
                {index === 0 ? <></> : <FontAwesomeIcon
                    icon={faAnglesUp}
                    className='card-white-icon'
                    onClick={() => moveCardToTop(index)}
                />}
                {index === cardDTOs.length - 1 ? <></> : <FontAwesomeIcon
                    icon={faAnglesDown}
                    className='card-white-icon'
                    onClick={() => moveCardToBottom(index)}
                />}
            </div>
        ))
    }
    const addNewCard = () => {
        setCreateDeckData(prevState => ({
            ...prevState,
            createDeckPayload: {
                ...prevState.createDeckPayload,
                cardDTOs: [...cardDTOs, defaultCreateCard]
            }
        }))
    }
    return (
        <Form onSubmit={(e) => onSubmit(e)}>
            <h2>
                Create New Deck In Here!
            </h2>
            <Form.Group>
                <Form.Control
                    autoFocus
                    type='text'
                    name='name'
                    value={name}
                    onChange={onChange}
                    placeholder='name'
                    className='name'
                />
            </Form.Group>
            <Form.Group>
                <Form.Control
                    autoFocus
                    type='text'
                    as='textarea'
                    rows={3}
                    name='description'
                    value={description}
                    onChange={onChange}
                    placeholder='description'
                    className='description'
                />
            </Form.Group>
            <Form.Group className='cards'>
                {cards()}
            </Form.Group>
            <Form.Group>
                <Button onClick={addNewCard} size='lg'>New Card</Button>
            </Form.Group>
            <Form.Group>
                <Button type='submit' disabled={false} size='lg'>Create Deck</Button>
            </Form.Group>
        </Form>
    )
}

export default CreateDeck
