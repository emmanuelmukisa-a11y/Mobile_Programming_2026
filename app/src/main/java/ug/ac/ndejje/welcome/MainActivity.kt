package ug.ac.ndejje.welcome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ug.ac.ndejje.welcome.ui.theme.NdejjeWelcomeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NdejjeWelcomeAppTheme {
                StudentDirectory()
                }
            }
        }
    }

@Composable
fun StudentInfo(student: Student) {
    Column( horizontalAlignment= Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxWidth().padding(16.dp) ) {
        Image(
            painter = painterResource(student.profileImageId),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(170.dp)
                .clip(shape = RoundedCornerShape(percent = 50))
                .padding(bottom = 8.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = student.name,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = student.regNumber,
            color = Color.Gray
        )
    }

}
@Composable
fun StudentIdCard(student: Student) {

    var isPresent by remember { mutableStateOf(false) }

    val borderColor = if (isPresent) Color.Green else Color.Transparent
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Column(modifier= Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            StudentInfo(student)
            Button(onClick= {/*Insert text*/} ) {
                Text("View Profile")}


            }
        }
    }

@Composable
fun StudentDirectory(){

    var searchQuery by remember {mutableStateOf("")}

    val filteredStudents = StudentProvider.studentList.filter{
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()){
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text(stringResource(R.string.search_placeholder))},
            leadingIcon= {
                Icon (Icons.Default.Search, contentDescription= "Search Icon")
            }
        )

        LazyColumn(contentPadding = PaddingValues(16.dp)){
            items(filteredStudents) { student ->
                StudentIdCard(student = student)
                Spacer(modifier = Modifier.height(12.dp))

            }
        }
    }



    /*val students = StudentProvider.studentList
    LazyColumn(
        modifier= Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)

    ){
        items(students){ student ->
            StudentIdCard(student = student)
            Spacer(modifier= Modifier.height(12.dp))
        }
    }
   */
}

@Preview(showBackground = true,
showSystemUi = true)
@Composable
fun WelcomePreview() {
    NdejjeWelcomeAppTheme() {
        StudentDirectory()
    }
}