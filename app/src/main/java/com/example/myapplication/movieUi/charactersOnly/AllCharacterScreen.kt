package com.example.myapplication.movieUi.charactersOnly

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.CharacterResult
import com.example.myapplication.data.ComicResults
import com.example.myapplication.data.SeriesResults
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberImagePainter
import com.example.myapplication.R
import com.example.myapplication.utilis.Constant
import com.example.myapplication.viewModel.CharacterViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CharacterScreen(characterViewModel: CharacterViewModel = viewModel()) {
    val showSearchBar = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Constant.CharacterConstant.MOVIECHARACTER) },
                backgroundColor = Color.Red,
                contentColor = Color.White,
                elevation = 12.dp
            )
        },
        //TODO search for item when button is clicked
        floatingActionButton = {
            FloatingActionButton(onClick = {showSearchBar.value = !showSearchBar.value }) {
                Icon(Icons.Filled.Search, contentDescription = Constant.CharacterConstant.SEARCHS)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            val showSearchBar = remember { mutableStateOf(false) }
            val charactersState = remember { mutableStateOf<List<CharacterResult>>(emptyList()) }
            val comicState = remember { mutableStateOf<List<ComicResults>>(emptyList()) }
            val seriesState = remember { mutableStateOf<List<SeriesResults>>(emptyList()) }
            val favoriteCharacters by characterViewModel.getFavouriteCharacters()
                .collectAsState(emptyList())
            var searchText by remember { mutableStateOf("") }


            LaunchedEffect(Unit) {
                charactersState.value = characterViewModel.getAllCharacters()
            }

            Column(modifier = Modifier.padding(2.dp)) {
                if (showSearchBar.value) {
                    SearchBar(onSearch = { query ->
//                        characterViewModel.searchCharacter(query)
                        showSearchBar.value = false
                    // Optionally hide searchBar after search
                    })
                }
                Spacer(modifier = Modifier.height(16.dp))
                val comicResults by comicState
                val seriesResults by seriesState


                CharacterList(

                    characters = if (searchText.isEmpty()) charactersState.value else favoriteCharacters,

                    onFavoriteToggle = { character, isFavourite ->
                        if (isFavourite) {
                            characterViewModel.removeCharacterFromFavourite(character)
                        } else {
                            characterViewModel.addCharacterToFavourite(character)
                        }
                    },
                    comicResults = comicResults, // Pass the list of comicResults
                    seriesResults = seriesResults

                )
                charactersState.value.forEach { character ->
                    LaunchedEffect(character.id) {
                        comicState.value = characterViewModel.getCharacterComics(character.id)
                        seriesState.value = characterViewModel.getCharacterSeries(character.id)
                    }
                }

            }
        }
    }
}
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = {
            searchText = it
            onSearch(it)
        },
        label = { Text(Constant.CharacterConstant.SEARCH) },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = Constant.CharacterConstant.ICONSEARCH) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(searchText)  // Trigger the search logic only when the user presses Search on the keyboard
        })
    )
}

@Composable
fun CharacterList(characters: List<CharacterResult>, comicResults: List<ComicResults>, seriesResults: List<SeriesResults>,
                  onFavoriteToggle: (CharacterResult, Boolean) -> Unit) {
    LazyColumn {
        items(characters) { character ->
            CharacterItem(
                character = character,
                comicResults = comicResults, // Pass the list of comicResults
                seriesResults = seriesResults
            ) { isFavourite ->
                onFavoriteToggle(character, isFavourite)
            }
        }
    }
}

@Composable
fun CharacterItem(
    character: CharacterResult,
    comicResults: List<ComicResults>,
    seriesResults: List<SeriesResults>,
    onFavoriteToggle: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val typography = MaterialTheme.typography

    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp)) // Rounded corners for the box
            .background(MaterialTheme.colors.surface)
            .shadow(4.dp, RoundedCornerShape(10.dp)) // Shadow for 3D effect
            .padding(16.dp)
    ) {
        Column(
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberImagePainter(
                        data = "${character.thumbnail.path}.${character.thumbnail.extension}",
                        builder = {
                            placeholder(R.drawable.dall_e_2024_04_30_20_26_47___a_superhero_themed_icon_suitable_for_an_android_app__featuring_a_stylized_shield_with_a_star_in_the_center__the_shield_is_metallic_red_and_blue__with_)
                            error(R.drawable.dall_e_2024_04_30_20_26_47___a_superhero_themed_icon_suitable_for_an_android_app__featuring_a_stylized_shield_with_a_star_in_the_center__the_shield_is_metallic_red_and_blue__with_)
                        }
                    ),
                    contentDescription = "${character.name} thumbnail",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        character.name,
                        style = typography.h6,
                        color = MaterialTheme.colors.onSurface
                    )
                    Text("ID: ${character.id}", style = typography.body2)
                    Text("ID: ${character.description}", style = typography.body2)
                    Text("ID: ${character.comics}", style = typography.body2)
                    Text("ID: ${character.series}", style = typography.body2)


                }
            }
            ExpandableSection(title = Constant.CharacterConstant.URLCLICK) {
                character.urls.forEach { url ->
                    ClickableText(
                        text = AnnotatedString(url.type + ": " + url.url),
                        onClick = {try {
                            val correctedUrl =
                                if (!url.url.startsWith("http://") && !url.url.startsWith("https://")) {
                                    "http://${url.url}"
                                } else {
                                    url.url
                                }

                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(correctedUrl))
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // Optionally inform the user that the URL couldn't be opened
                        }
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            ExpandableSection(title = Constant.CharacterConstant.COMICCLICK) {
                if (comicResults.isNotEmpty()) {
                    comicResults.forEach { comic ->
                        Text(text = comic.id ?: "No title available")
                        Text(text = comic.title ?: "No title available")
                        Text(text = comic.description ?: "No title available")
                        Text(text = comic.thumbnail.path ?: "No title available")
                    }
                } else {
                    Text(Constant.CharacterConstant.COMICNOTAVAILABLE)
                }
            }

            ExpandableSection(title = Constant.CharacterConstant.SERIESCLICK) {
                if (seriesResults.isNotEmpty()) {
                    seriesResults.forEach { series ->
                        Text(text = series.id ?: "No title available")
                        Text(text = series.title ?: "No title available")
                        Text(text = series.description ?: "No title available")
                        Text(text = series.thumbnail.path ?: "No title available")
                    }
                } else {
                    Text(Constant.CharacterConstant.SERIESNOTAVAILABLE)
                }
            }
        }
    }
}

@Composable
fun ExpandableSection(
    title: String,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(12.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        if (expanded) {
            content()
        }
    }
}
