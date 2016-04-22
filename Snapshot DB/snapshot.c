/**
 * comp2129 - assignment 2
 * <Roman Pazinich>
 * <440012959>
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <stdbool.h>

#include "snapshot.h"

//this funcion frees all the memory that has been dynamically allocated for the given entry list
void free_entrylist_space(entry *entryHead) {
	entry *currentEntry = entryHead;
	while (currentEntry != NULL) {
		free(currentEntry->values);
		entry *temp = currentEntry;
		currentEntry = currentEntry->next;
		free(temp);
	}
}

//this funcion frees all the memory that has been dynamically allocated for the given snapshot list
void free_snapshotlist_space(snapshot *snapHead) {
	snapshot *currentSnap = snapHead;
	while (currentSnap != NULL) {
		free_entrylist_space(currentSnap->entries);
		snapshot *temp = currentSnap;
		currentSnap = currentSnap->next;
		free(temp);
	}	
}

//frees all the memory that has been dynamically allocated during execution
void command_bye(entry *entryHead, snapshot *snapHead) {
	free_entrylist_space(entryHead);
	free_snapshotlist_space(snapHead);
	printf("bye\n");	
}

void command_help() {
	printf("%s\n", HELP);
}

//returns an entry object whose key is the same as the one that is passed to the function
//if the entry is not found it returns null
entry* get_entry(entry *entryHead, char key[MAX_KEY]) {
	
	entry *currentEntry = entryHead;
	
	while(currentEntry != NULL) {
		if (strcmp(currentEntry->key, key) == 0) {
			return currentEntry;	
		}
		currentEntry = currentEntry->next;
	}
	
	return NULL;
}

//this function reads values from user input, stores them in the allocated memory and returns a pointer to them
int* read_values(char *ptr, size_t *length) {
	
	int *values = (int *) malloc(sizeof(int));
	
	ptr = strtok(NULL, " ");
	
	//if there are no values to read then free the allocated space and return null
	if (ptr == NULL || *ptr == '\n') {
		free(values);
		return NULL;	
	}
	
	//read all the values from the input and store them in the allocated memory
	while (ptr != NULL) {
		if (*ptr == '\n') {
			break;
		}
		*length = *length + 1;
		values = (int *) realloc(values, sizeof(int)*(*length));
		
		values[*length - 1] = atoi(ptr);
		ptr = strtok(NULL, " ");
	}
	
	return values;
}

//initializes a new entry list
entry* entrylist_init(char key[MAX_KEY]) {
	entry* newEntry = (entry *) malloc(sizeof(entry));
	newEntry->prev = NULL;
	newEntry->next = NULL;
	strcpy(newEntry->key, key);
	newEntry->values = NULL;
	newEntry->length = 0;
	
	return newEntry;  //new head of the list
}

//adds a new entry to the beginning of the list
entry* entry_add(entry *entryHead, char key[MAX_KEY]) {
	entry *newEntry = (entry *) malloc(sizeof(entry));
	newEntry->prev = NULL;
	newEntry->next = entryHead;
	entryHead->prev = newEntry;
	newEntry->length = 0;
	newEntry->values = NULL;
	strcpy(newEntry->key, key);
	
	return newEntry;  //new head of the list
}

//removes an entry from the list
entry* entry_delete(entry *entryHead, entry *unwantedEntry) {
	entry *returnEntry; //this variable will store the address of the updated head of the list
	
	//the entry that is to be deleted is neither the first nor the last entry --> return the old head
	if (unwantedEntry->next != NULL && unwantedEntry->prev != NULL) {
		unwantedEntry->prev->next = unwantedEntry->next;
		unwantedEntry->next->prev = unwantedEntry->prev;
		returnEntry = entryHead;
	}
	//the entry is the last entry in the list --> return the old head
	else if (unwantedEntry->next == NULL && unwantedEntry->prev != NULL) {
		unwantedEntry->prev->next = NULL;
		returnEntry = entryHead;
	}
	//the entry is the head of the list --> set the head to point to the next entry
	else if (unwantedEntry->next != NULL && unwantedEntry->prev == NULL) {
		unwantedEntry->next->prev = NULL;
		returnEntry = unwantedEntry->next;
	}
	//the entry is the only entry in the list --> return NULL
	else {
		returnEntry = NULL;	
	}
		
	free(unwantedEntry->values);
	free(unwantedEntry);
	return returnEntry;
}

//deletes an entry from the list which stores the given key
entry* del(entry *entryHead, char key[MAX_KEY]) {
	entry *unwantedEntry = get_entry(entryHead, key);
	
	if (unwantedEntry == NULL) {
		printf("no such key\n");
		return entryHead;
	}
	else {
		printf("ok\n");
		return entry_delete(entryHead, unwantedEntry);	
	}
}

//depending on the command passed executes either PUSH or APPEND function
void push_append(char *ptr, char key[MAX_KEY], entry *entryHead, char command[MAX_COMMAND]) {
	entry *chosenEntry = get_entry(entryHead, key);
	
	if (chosenEntry == NULL) {
		printf("no such key\n");
		return;
	}
	else {
		printf("ok\n");	
	}
	
	size_t newValLength = 0;
	int *newValues = read_values(ptr, &newValLength); //get values to push or append
	int *oldValues = chosenEntry->values;  //get values that the entry already stores
	
	size_t oldValLength = chosenEntry->length;
	size_t totalLength = oldValLength + newValLength;
	
	//PUSH
	if (strcasecmp(command, "PUSH") == 0) {
		int *allValues = (int *) malloc(sizeof(int)*totalLength); //allocate enough memory for both new and old values
		int counter = 0;
		
		//push new values to the allocated space
		for (int i = newValLength - 1; i >= 0; i--) {
			allValues[counter] = newValues[i];
			counter++;
		}
		
		//copy old values to the allocated space
		for (int i = 0; i < oldValLength; i++) {
			allValues[counter] = oldValues[i];
			counter++;
		}
		
		free(oldValues);
		chosenEntry->values = allValues;
	}
	//APPEND
	else {
		oldValues = (int *) realloc(oldValues, sizeof(int)*totalLength);  //allocate more space to the memory where old values are stored
	
		//copy all new values to the memory space where the old values are stored
		for (int counter = 0; counter < newValLength; counter++) {
			oldValues[oldValLength + counter] = newValues[counter];	
		}
		
		chosenEntry->values = oldValues;
	}
	
	free(newValues);
	chosenEntry->length = totalLength;
}

//adds a new entry to the list with the values user inputs
entry* set(char *ptr, char key[MAX_KEY], entry *entryHead) {
	//list is empty, initialize a new list
	if (entryHead == NULL) {
		entryHead = (entry *) entrylist_init(key);	
	}
	
	//find entry with the specified key
	entry *chosenEntry = get_entry(entryHead, key);
	
	//entry with such a key does not exist. add new entry to the list
	if (chosenEntry == NULL) {
		chosenEntry = entry_add(entryHead, key);
		entryHead = chosenEntry;
	}
	
	//if the entry already has values, free the memory that is allocated for those values
	if (chosenEntry->values != NULL) {
		free(chosenEntry->values);	
	}
	
	size_t length = 0;
	chosenEntry->values = read_values(ptr, &length);
	chosenEntry->length = length;
	printf("ok\n");
	
	return entryHead;  //updated head of the list
}

//prints all values that the given entry stores
void print_values(entry *inputEntry) {
	int *values = inputEntry->values;
	size_t length = inputEntry->length;
		
	if (length == 0) {
		printf("[]\n");
		return;
	}
		
	printf("[");
	for (int i = 0; i < length - 1; i++) {
		printf("%d ", values[i]);
	}
	printf("%d]\n", values[length - 1]);
}

//prints keys and values of all the entries in the list
void list_entries(entry *entryHead) {
	
	if (entryHead == NULL) {
		printf("no entries\n");
		return;
	}
	
	entry *currentEntry = entryHead;
	
	while(currentEntry != NULL) {
		printf("%s ", currentEntry->key);
		print_values(currentEntry);
		currentEntry = currentEntry->next;
	}
}

//prints ids of all the snapshots that have been made
void list_snapshots(snapshot *snapHead) {
	if (snapHead == NULL) {
		printf("no snapshots\n");
		return;
	}
	
	snapshot *currentSnap = snapHead;
	while (currentSnap != NULL) {
		printf("%d\n", currentSnap->id);
		currentSnap = currentSnap->next;
	}
}

//prints all values of the entry with the given key
void get(char key[MAX_KEY], entry *entryHead) {
	entry *inputEntry = get_entry(entryHead, key);
	if (inputEntry == NULL) {
		printf("no such key\n");
	}
	else {
		print_values(inputEntry);
	}
}

//prints keys of all entries in the list
void list_keys(entry *entryHead) {
	
	entry *currentEntry = entryHead;
	
	if (currentEntry == NULL) {
		printf("no keys\n");
		return;
	}
	
	while (currentEntry != NULL) {
		printf("%s\n", currentEntry->key);
		currentEntry = currentEntry->next;
	}
}

//displays value at index
int pick(int index, char key[MAX_KEY], entry *entryHead) {
	entry *chosenEntry = get_entry(entryHead, key);
	
	//check if the input is valid
	if (chosenEntry == NULL) {
		printf("no such key\n");
		return 0;
	}
	else if (chosenEntry->length == 0) {
		printf("nil\n");
		return 0;
	}
	else if (index < 1 || index > chosenEntry->length) {
		printf("index out of range\n");
		return 0;
	}
	
	//print a value which corresponds to the given index
	printf("%d\n", chosenEntry->values[index - 1]);
	return 1;
}

//displays and removes value at index
void pluck(int index, char key[MAX_KEY], entry *entryHead) {
	if (pick(index, key, entryHead) == 0) {
		return;
	}
	
	entry *chosenEntry = get_entry(entryHead, key);
	
	size_t newLength = chosenEntry->length - 1;
	int *newValues = (int *) malloc(sizeof(int)*newLength);
	
	//copy all the values to a new chunk of allocated memory 
	//except for the one which corresponds to the given index
	int newValCounter = 0;
	int oldValCounter = 0;
	while (newValCounter < newLength) {
		if (oldValCounter != index - 1) {
			newValues[newValCounter] = chosenEntry->values[oldValCounter];
			newValCounter++;
		}
			oldValCounter++;
	}
	
	//free the memory which was allocated for the old values and update the chosen entry
	free(chosenEntry->values);
	chosenEntry->length = newLength;
	chosenEntry->values = newValues;
}

//returns a snapshot object whose id is the same as the one that is passed to the function
//if the snapshot is not found it returns null
snapshot* get_snapshot(snapshot *snapHead, int id) {
	
	snapshot *currentSnap = snapHead;
	
	while(currentSnap != NULL) {
		if (currentSnap->id == id) {
			return currentSnap;	
		}
		currentSnap = currentSnap->next;
	}
	
	return NULL;
}

//removes snapshot from the list 
snapshot* snapshot_delete(snapshot *snapHead, snapshot *unwantedSnap) {
	snapshot *returnSnap; //this variable will store the address of the updated head of the list
	
	//the snapshot that is to be deleted is neither the first nor the last snapshot --> return the old head
	if (unwantedSnap->next != NULL && unwantedSnap->prev != NULL) {
		unwantedSnap->prev->next = unwantedSnap->next;
		unwantedSnap->next->prev = unwantedSnap->prev;
		returnSnap = snapHead;
	}
	//the entry is the last snapshot in the list --> return the old head
	else if (unwantedSnap->next == NULL && unwantedSnap->prev != NULL) {
		unwantedSnap->prev->next = NULL;
		returnSnap = snapHead;
	}
	//the snapshot is the head of the list --> set the head to point to the next snapshot
	else if (unwantedSnap->next != NULL && unwantedSnap->prev == NULL) {
		unwantedSnap->next->prev = NULL;
		returnSnap = unwantedSnap->next;
	}
	//the snapshot is the only snapshot in the list --> return NULL
	else {
		returnSnap = NULL;	
	}
		
	free_entrylist_space(unwantedSnap->entries);
	free(unwantedSnap);
	return returnSnap;
}

//removes a snapshot with the given id from the list
snapshot* drop(snapshot *snapHead, int id) {
	snapshot *unwantedSnap = get_snapshot(snapHead, id);
	
	if (unwantedSnap == NULL) {
		printf("no such snapshot\n");
		return snapHead;
	}
	else {
		printf("ok\n");
		return snapshot_delete(snapHead, unwantedSnap);	
	}
}

//adds a snapshot to the beginning of the list
snapshot* snapshot_add(snapshot *snapHead) {
	snapshot *newSnap = (snapshot *) malloc(sizeof(snapshot));
	newSnap->prev = NULL;
	newSnap->entries = NULL;
	
	if (snapHead == NULL) {
		newSnap->id = 1;
		newSnap->next = NULL;
	}
	else {
		newSnap->id = snapHead->id + 1;
		newSnap->next = snapHead;
		snapHead->prev = newSnap;
	}
	
	return newSnap;
}

//copies all values from the given array to a new one and returns a pointer to the new array
int* get_values_copy(int *source, int length) {
	if (source == NULL) {
		return NULL;
	}
	
	int *destination = (int *) malloc(sizeof(int)*length);
	
	for(int counter = 0; counter < length; counter++) {
		destination[counter] = source[counter];	
	}
	
	return destination;
}

//returns a reversed copy of all the entries that the given entrylist stores
entry* get_reversed_entrylist_copy(entry *source) {
	if (source == NULL) {
		return NULL;	
	}
	
	//initialize a new entrylist and copy the first entry (which is head of the list)
	entry *destination = entrylist_init(source->key);
	destination->values = get_values_copy(source->values, source->length);
	destination->length = source->length;
			
	//copy the rest of the entries from source entry list to destination entrylist 
	entry *currentSourceEntry = source->next;
	while (currentSourceEntry != NULL) {
		destination = entry_add(destination, currentSourceEntry->key);
		destination->values = get_values_copy(currentSourceEntry->values, currentSourceEntry->length);
		destination->length = currentSourceEntry->length;
		
		currentSourceEntry = currentSourceEntry->next;
	}
	
	return destination;
}

//saves the current state as a snapshot
snapshot* take_snapshot(entry *entryHead, snapshot *snapHead) {
	snapshot *newSnap = snapshot_add(snapHead);
	newSnap->entries = get_reversed_entrylist_copy(entryHead);
	
	printf("saved as snapshot %d\n", newSnap->id);
	
	return newSnap;
}

//removes all snapshots from the list that were taken after the one with the given id
snapshot* remove_snapshots(int id, snapshot *snapHead) {
	snapshot *chosenSnap = get_snapshot(snapHead, id);
	snapshot *newSnapHead = snapHead;
	if (chosenSnap == NULL) {
		return newSnapHead;	
	}
	
	chosenSnap = chosenSnap->prev;
	while (chosenSnap != NULL) {
		snapshot *temp = chosenSnap->prev;
		newSnapHead = snapshot_delete(newSnapHead, chosenSnap);
		chosenSnap = temp;
	}	
	
	return newSnapHead;
}

//replaces current state with a copy of snapshot
entry* checkout(int id, entry *entryHead, snapshot *snapHead) {
	snapshot *chosenSnap = get_snapshot(snapHead, id);
	
	if (chosenSnap == NULL) {
		printf("no such snapshot\n");
		return entryHead;
	}
	
	printf("ok\n");
	free_entrylist_space(entryHead);
	return get_reversed_entrylist_copy(chosenSnap->entries);
}

//deletes entry from current state and snapshots
entry* purge(char key[MAX_KEY], entry *entryHead, snapshot *snapHead) {
	//remove entry from current state
	entry *unwantedEntry = get_entry(entryHead, key);
	if (unwantedEntry != NULL) {
		entryHead = entry_delete(entryHead, unwantedEntry);	
	}
	
	//remove entry from each snapshot
	snapshot *currentSnap = snapHead;
	while (currentSnap != NULL) {
		unwantedEntry = get_entry(currentSnap->entries, key);
		if (unwantedEntry != NULL) {
			currentSnap->entries = entry_delete(currentSnap->entries, unwantedEntry);
		}
		currentSnap = currentSnap->next;
	}
		
	printf("ok\n");

	return entryHead;
}

//displays minimum value
void min(char key[MAX_KEY], entry *entryHead) {
	entry *chosenEntry = get_entry(entryHead, key);
	
	if (chosenEntry == NULL) {
		printf("no such key\n");
		return;	
	}
	else if (chosenEntry->length == 0) {
		printf("nil\n");
		return;
	}
	
	int min = chosenEntry->values[0];
	for (int counter = 1; counter < chosenEntry->length; counter++) {
		if (chosenEntry->values[counter] < min) {
			min = chosenEntry->values[counter];	
		}
	}
	printf("%d\n", min);
}

//displays maximum value
void max(char key[MAX_KEY], entry *entryHead) {
	entry *chosenEntry = get_entry(entryHead, key);
	
	if (chosenEntry == NULL) {
		printf("no such key\n");
		return;	
	}
	else if (chosenEntry->length == 0) {
		printf("nil\n");
		return;
	}
	
	int max = chosenEntry->values[0];
	for (int counter = 1; counter < chosenEntry->length; counter++) {
		if (chosenEntry->values[counter] > max) {
			max = chosenEntry->values[counter];	
		}
	}
	printf("%d\n", max);
}

//displays sum of values
void sum(char key[MAX_KEY], entry *entryHead) {
	entry *chosenEntry = get_entry(entryHead, key);
	
	if (chosenEntry == NULL) {
		printf("no such key\n");
		return;	
	}
	else if (chosenEntry->length == 0) {
		printf("0\n");
		return;
	}
	
	int sum = 0;
	for (int counter = 0; counter < chosenEntry->length; counter++) {
		sum += chosenEntry->values[counter];
	}
	printf("%d\n", sum);
}

//displays number of values
void len(char key[MAX_KEY], entry *entryHead) {
	entry *chosenEntry = get_entry(entryHead, key);
	
	if (chosenEntry == NULL) {
		printf("no such key\n");
	}
	else {
		printf("%zu\n", chosenEntry->length);	
	}
}

//reverses order of values
void reverse(char key[MAX_KEY], entry *entryHead) {
	entry *chosenEntry = get_entry(entryHead, key);
	if (chosenEntry == NULL) {
		printf("no such key\n");
		return;
	}
	else {
		printf("ok\n");	
	}
	
	int *reversedValues = (int *) malloc(sizeof(int)*chosenEntry->length);
	for (int counter = 0; counter < chosenEntry->length; counter++) {
		reversedValues[counter] = chosenEntry->values[chosenEntry->length - counter - 1];	
	}
	free(chosenEntry->values);
	chosenEntry->values = reversedValues;
}

int* get_unique_array(int *array, int length, int *counter) {
	int *uniqueValues = NULL;
	
	for (int i = 0; i < length; i++) {
		if (*counter == 0 || uniqueValues[*counter - 1] != array[i]) {
			uniqueValues = (int *) realloc(uniqueValues, sizeof(int)*(*counter + 1));
			uniqueValues[*counter] = array[i];
			(*counter)++;
		}
	}
	
	return uniqueValues;
}	

//removes repeated adjacent values
void unique(char key[MAX_KEY], entry *entryHead) {
	entry *chosenEntry = get_entry(entryHead, key);
	if (chosenEntry == NULL) {
		printf("no such key\n");
		return;
	}
	else {
		printf("ok\n");	
	}
	
	//int *uniqueValues = NULL;
	int counter = 0;
	int *uniqueValues = get_unique_array(chosenEntry->values, chosenEntry->length, &counter);
	
	free(chosenEntry->values);
	chosenEntry->values = uniqueValues;
	chosenEntry->length = counter;
}

void sort_array(int *values, int length) {
	for (int i = length - 1; i >= 0; i--) {
		for (int j = 1; j <= i; j++) {
			if (values[j - 1] > values[j]) {
				int temp = values[j - 1];
				values[j - 1] = values[j];
				values[j] = temp;
			}
		}
	}	
}

//sorts values in ascending order
void sort(char key[MAX_KEY], entry *entryHead) {
	entry *chosenEntry = get_entry(entryHead, key);
	if (chosenEntry == NULL) {
		printf("no such key\n");
		return;
	}
	else {
		printf("ok\n");	
	}
	sort_array(chosenEntry->values, chosenEntry->length);
}

char* get_keys(char *ptr, char key[MAX_KEY], int *length) {
	char *keys = (char *) malloc(MAX_KEY*sizeof(char)*(*length));
	strcpy(keys, key);
	
	ptr = strtok(NULL, " ");
	while (ptr != NULL) {
		if (*ptr == '\n') {
			break;
		}
		(*length)++;
		keys = (char *) realloc(keys, sizeof(char)*(*length)*MAX_KEY);
		
		strcpy(&keys[MAX_KEY*(*length - 1)], ptr);
		ptr = strtok(NULL, " ");
	}
	
	char *newLine;
	newLine = strchr(&keys[MAX_KEY*(*length - 1)], '\n');
	if (newLine) {
		*newLine = 0;	
	}
	
	return keys;
}

void diff(char *ptr, char key[MAX_KEY], entry *entryHead) {
	int keysLength = 1;
	char *keys = get_keys(ptr, key, &keysLength);
	
	if (keysLength == 1) {
		printf("invalid input\n");
		free(keys);
		return;
	}
	
	entry *lastEntry = get_entry(entryHead, &keys[MAX_KEY*(keysLength - 1)]);
	if (lastEntry == NULL) {
		printf("no such key\n");
		free(keys);
		return;
	}
	int *processedValues = get_values_copy(lastEntry->values, lastEntry->length);
	int valuesLength = lastEntry->length;
	
	for (int i = keysLength - 2; i >= 0; i--) {
		entry *first = get_entry(entryHead, &keys[MAX_KEY*i]);
		if (first == NULL) {
			printf("no such key\n");
			free(processedValues);
			free(keys);
			return;
		}
		int firstLength = first->length;
		
		int *firstValues = get_values_copy(first->values, firstLength);
		sort_array(firstValues, firstLength);
		
		int firstUniqLength = 0;
		int *uniqFirst = get_unique_array(firstValues, firstLength, &firstUniqLength);
		free(firstValues);
		
		if (firstUniqLength == 0) {
			free(processedValues);
			processedValues = NULL;
			valuesLength = 0;
			continue;
		}
		else if (valuesLength == 0) {
			processedValues = uniqFirst;
			valuesLength = firstUniqLength;
			continue;
		}
		
		int * newValues = NULL;
		int newLength = 0;
		
		for (int i = 0; i < firstUniqLength; i++) {
			int exists = 0;
			
			for (int j = 0; j < valuesLength; j++) {
				if (processedValues[j] == uniqFirst[i]) {
					exists = 1;	
				}
			}
			
			if (exists == 0) {
				newLength++;
				newValues = (int *) realloc(newValues, newLength*sizeof(int));
				newValues[newLength - 1] = uniqFirst[i];
			}
		}
		
		free(uniqFirst);
		free(processedValues);
		processedValues = newValues;
		valuesLength = newLength;
	}

	free(keys);
	
	if (valuesLength == 0) {
		printf("[]\n");
		return;
	}
	
	printf("[");
	for (int i = 0; i < valuesLength - 1; i++) {
		printf("%d ", processedValues[i]);	
	}
	printf("%d]\n", processedValues[valuesLength - 1]);
	
	free(processedValues);
}

void inter(char *ptr, char key[MAX_KEY], entry *entryHead) {
	int keysLength = 1;
	char *keys = get_keys(ptr, key, &keysLength);
	
	if (keysLength == 1) {
		printf("invalid input\n");
		free(keys);
		return;
	}
	
	entry *lastEntry = get_entry(entryHead, &keys[MAX_KEY*(keysLength - 1)]);
	if (lastEntry == NULL) {
		printf("no such key\n");
		free(keys);
		return;
	}
	int *processedValues = get_values_copy(lastEntry->values, lastEntry->length);
	int valuesLength = lastEntry->length;
	
	for (int i = keysLength - 2; i >= 0; i--) {
		entry *first = get_entry(entryHead, &keys[MAX_KEY*i]);
		if (first == NULL) {
			printf("no such key\n");
			return;
		}
		int firstLength = first->length;
		
		int *firstValues = get_values_copy(first->values, firstLength);
		sort_array(firstValues, firstLength);
		
		int firstUniqLength = 0;
		int *uniqFirst = get_unique_array(firstValues, firstLength, &firstUniqLength);
		free(firstValues);
		
		if (firstUniqLength == 0) {
			free(processedValues);
			processedValues = NULL;
			valuesLength = 0;
			continue;
		}
		else if (valuesLength == 0) {
			processedValues = NULL;
			valuesLength = 0;
			free(uniqFirst);
			continue;
		}
		
		int * newValues = NULL;
		int newLength = 0;
		
		for (int i = 0; i < firstUniqLength; i++) {
			int exists = 0;
			
			for (int j = 0; j < valuesLength; j++) {
				if (processedValues[j] == uniqFirst[i]) {
					exists = 1;	
				}
			}
			
			if (exists == 1) {
				newLength++;
				newValues = (int *) realloc(newValues, newLength*sizeof(int));
				newValues[newLength - 1] = uniqFirst[i];
			}
		}
		
		free(uniqFirst);
		free(processedValues);
		processedValues = newValues;
		valuesLength = newLength;
	}

	free(keys);
	
	if (valuesLength == 0) {
		printf("[]\n");
		return;
	}
	
	printf("[");
	for (int i = 0; i < valuesLength - 1; i++) {
		printf("%d ", processedValues[i]);	
	}
	printf("%d]\n", processedValues[valuesLength - 1]);
	
	free(processedValues);
}

void union_oper(char *ptr, char key[MAX_KEY], entry *entryHead) {
	int keysLength = 1;
	char *keys = get_keys(ptr, key, &keysLength);
	
	if (keysLength == 1) {
		printf("invalid input\n");
		free(keys);
		return;
	}
	
	entry *lastEntry = get_entry(entryHead, &keys[MAX_KEY*(keysLength - 1)]);
	if (lastEntry == NULL) {
		printf("no such key\n");
		free(keys);
		return;
	}
	int *processedValues = get_values_copy(lastEntry->values, lastEntry->length);
	int valuesLength = lastEntry->length;
	
	for (int i = keysLength - 2; i >= 0; i--) {
		entry *first = get_entry(entryHead, &keys[MAX_KEY*i]);
		if (first == NULL) {
			printf("no such key\n");
			return;
		}
		
		processedValues = (int *) realloc(processedValues, sizeof(int)*(valuesLength + first->length));
		int counter = 0;
		for (int i = valuesLength; i < valuesLength + first->length; i++) {
			processedValues[i] = first->values[counter];
			counter++;
		}
		valuesLength = valuesLength + first->length;
	}

	free(keys);
	
	if (valuesLength == 0) {
		printf("[]\n");
		free(processedValues);
		return;
	}
	
	sort_array(processedValues, valuesLength);
	
	int tempLength = 0;
	int *uniqProcessedValues = get_unique_array(processedValues, valuesLength, &tempLength);
	
	printf("[");
	for (int i = 0; i < tempLength - 1; i++) {
		printf("%d ", uniqProcessedValues[i]);	
	}
	printf("%d]\n", uniqProcessedValues[tempLength - 1]);
	
	free(uniqProcessedValues);
	free(processedValues);
}		

int main(void) {

	char line[MAX_LINE];
	entry *entryHead;       //head of entry list
	snapshot *snapHead;		//head of snapshot list
	entryHead = NULL;  
	snapHead = NULL;
	
	while (true) {
		printf("> ");

		if (fgets(line, MAX_LINE, stdin) == NULL) {
			printf("\n");
			command_bye(entryHead, snapHead);
			return 0;
		}
		
		//get command from the input line as well as a second parameter if it exists
		char *ptr = strtok(line, " ");
		char firstToken[MAX_COMMAND];    //stores a command
		strcpy(firstToken, ptr);
		ptr = strtok(NULL, " ");
		char secondToken[MAX_KEY];  	 //stores either a key or a second part of the command
		secondToken[0] = '\0';
		
		//get rid of a new line character if it exists in the last valid token entered
		char *newLine;
		if (ptr != NULL) {		
			strcpy(secondToken, ptr);
			newLine = strchr(secondToken, '\n');
		}
		else {
			newLine = strchr(firstToken, '\n');
		}
		if (newLine) {
			*newLine = 0;	
		}
		
		//identify the command and call the right function to execute the command entered
		if (strcasecmp(firstToken, "BYE") == 0) {
			command_bye(entryHead, snapHead);
			return 0;
		}
		else if (strcasecmp(firstToken, "HELP") == 0) {
			command_help();
		}
		else if (strcasecmp(firstToken, "LIST") == 0) {
			if (strcasecmp(secondToken, "ENTRIES") == 0) {
				list_entries(entryHead);	
			}
			else if (strcasecmp(secondToken, "KEYS") == 0) {
				list_keys(entryHead);
			}
			else if(strcasecmp(secondToken, "SNAPSHOTS") == 0) {
				list_snapshots(snapHead);
			}
			else {
				printf("unknown\n");	
			}
		}
		else if (strcasecmp(firstToken, "GET") == 0) {
			get(secondToken, entryHead);
		}
		else if (strcasecmp(firstToken, "DEL") == 0) {
			entryHead = del(entryHead, secondToken);
		}
		else if (strcasecmp(firstToken, "PURGE") == 0) {
			entryHead = purge(secondToken, entryHead, snapHead);
		}
		else if (strcasecmp(firstToken, "SET") == 0) {
			if (ptr != NULL && secondToken[0] != '\0') {
				entryHead = set(ptr, secondToken, entryHead);
			}
			else {
				printf("invalid input\n");	
			}
		}
		else if (strcasecmp(firstToken, "PUSH") == 0 || strcasecmp(firstToken, "APPEND") == 0) {
			push_append(ptr, secondToken, entryHead, firstToken);
		}
		else if (strcasecmp(firstToken, "PICK") == 0) {
			ptr = strtok(NULL, " ");
			int index = atoi(ptr);
			pick(index, secondToken, entryHead);
		}
		else if (strcasecmp(firstToken, "PLUCK") == 0) {
			ptr = strtok(NULL, " ");
			int index = atoi(ptr);
			pluck(index, secondToken, entryHead);
		}
		else if (strcasecmp(firstToken, "POP") == 0) {
			pluck(1, secondToken, entryHead);
		}
		else if (strcasecmp(firstToken, "DROP") == 0) {
			snapHead = drop(snapHead, atoi(secondToken));
		}
		else if (strcasecmp(firstToken, "ROLLBACK") == 0) {
			entryHead = checkout(atoi(secondToken), entryHead, snapHead);
			snapHead = remove_snapshots(atoi(secondToken), snapHead);
		}
		else if (strcasecmp(firstToken, "CHECKOUT") == 0) {
			entryHead = checkout(atoi(secondToken), entryHead, snapHead);
		}
		else if (strcasecmp(firstToken, "SNAPSHOT") == 0) {
			snapHead = take_snapshot(entryHead, snapHead);
		}
		else if (strcasecmp(firstToken, "MIN") == 0) {
			min(secondToken, entryHead);
		}
		else if (strcasecmp(firstToken, "MAX") == 0) {
			max(secondToken, entryHead);
		}
		else if (strcasecmp(firstToken, "SUM") == 0) {
			sum(secondToken, entryHead);
		}
		else if (strcasecmp(firstToken, "LEN") == 0) {
			len(secondToken, entryHead);
		}
		else if (strcasecmp(firstToken, "REV") == 0) {
			reverse(secondToken, entryHead);
		}
		else if (strcasecmp(firstToken, "UNIQ") == 0) {
			unique(secondToken, entryHead);
		}
		else if (strcasecmp(firstToken, "SORT") == 0) {
			sort(secondToken, entryHead);
		}
		else if (strcasecmp(firstToken, "DIFF") == 0) {
			diff(ptr, secondToken, entryHead);
		}
		else if (strcasecmp(firstToken, "INTER") == 0) {
			inter(ptr, secondToken, entryHead);
		}
		else if (strcasecmp(firstToken, "UNION") == 0) {
			union_oper(ptr, secondToken, entryHead);
		}
		else {
			printf("unknown\n");	
		}
		
		printf("\n");
  	}

	return 0;
}