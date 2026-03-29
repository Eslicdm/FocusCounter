**Objective**: A counter that the user clicks to increase focus and track progression. Example: the user mentally counts numbers in sequence (1, 2...) and clicks for each number; at the end, a focus score is generated showing the progression of focus improvement.

**Kotlin Multiplatform (Android Focused):**
* SqlDelight

**Kotlin Spring Boot:**
* PostgreSQL

**Instructions:**
* click the reset button to save the session (encourage message to click reset)

**Tips:** 
* use this app to avoid some unhealthy habit

**Charts:**
* Usage peak chart: x-axis with hours and y-axis with days of the week (M, T, W...) and a heatmap of total minutes (0-5, 5-10);
* Current Streak: Number of consecutive days with sessions longer than X minutes.
* Evolution Delta: "Your focus this month is 12% more stable than last month".
* Peak Focus Hour: "Your best focus time is at 10:00 AM".
* Show the total number of clicks;
* Show the total time elapsed from the first click to the last click (per round/session and daily total);
* Show the average time between clicks (per round/session and daily total);
* Show the Focus Score (combination of total time with the lowest standard deviation between clicks);

```kotlin
@Entity 
class FocusSession( 
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    val id: Long? = null, 
    val startTime: LocalDateTime, 
    val endTime: LocalDateTime, 
    val totalClicks: Int, 
    @ElementCollection // Stores deltas in milliseconds between each click 
    val intervals: List<Long>, 
    val focusScore: Double, 
    val userId: String 
)
```

**Tasks**:
* create a configuration screen and put in the navigation drawer
* Option to enable sound when clicking;
* count timestamps
* Create an app icon

