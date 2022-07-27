import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void beforeEachTest() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = Mockito.spy(new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime));
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime workingHour = LocalTime.parse("12:30:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(workingHour);
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime nonWorkingHour = LocalTime.parse("23:30:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(nonWorkingHour);
        assertFalse(restaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER COST<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void calculateTotalOrderCost_should_return_correct_total_price_when_single_item_is_added(){
        String[] selectedItems = {"Sweet corn soup"};
        int totalOrderCost = restaurant.calculateTotalOrderCost(selectedItems);
        assertEquals(119, totalOrderCost);
    }

    @Test
    public void calculateTotalOrderCost_should_return_correct_total_price_when_multiple_items_are_added() {
        //Adding one more item to menu
        restaurant.addToMenu("Coffee",199);
        String[] selectedItems = {"Sweet corn soup", "Vegetable lasagne", "Coffee"};
        int totalOrderCost = restaurant.calculateTotalOrderCost(selectedItems);
        assertEquals(119+269+199, totalOrderCost);
    }

    @Test
    public void calculateTotalOrderCost_should_return_correct_total_price_when_multiple_items_are_added_and_then_one_item_is_removed() {
        //Adding one more item to menu
        restaurant.addToMenu("Coffee",199);
        String[] selectedItems = {"Sweet corn soup", "Vegetable lasagne", "Coffee"};
        int totalOrderCost = restaurant.calculateTotalOrderCost(selectedItems);
        assertEquals(119+269+199, totalOrderCost);

        //Removing 'Sweet corn soup' from selected items
        String[] newSelectedItems = {"Vegetable lasagne", "Coffee"};
        totalOrderCost = restaurant.calculateTotalOrderCost(newSelectedItems);
        assertEquals(269+199, totalOrderCost);
    }

    @Test
    public void calculateTotalOrderCost_should_return_zero_total_price_when_no_item_is_selected() {
        String[] selectedItems = {};
        int totalOrderCost = restaurant.calculateTotalOrderCost(selectedItems);
        assertEquals(0, totalOrderCost);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<ORDER COST>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}