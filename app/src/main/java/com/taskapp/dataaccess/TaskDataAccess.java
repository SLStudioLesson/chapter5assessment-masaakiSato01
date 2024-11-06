package com.taskapp.dataaccess;

import com.taskapp.model.Task;
import com.taskapp.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDataAccess {

    private final String filePath;

    private final UserDataAccess userDataAccess;

    public TaskDataAccess() {
        filePath = "app/src/main/resources/tasks.csv";
        userDataAccess = new UserDataAccess();
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     *
     * @param filePath
     * @param userDataAccess
     */
    public TaskDataAccess(String filePath, UserDataAccess userDataAccess) {
        this.filePath = filePath;
        this.userDataAccess = userDataAccess;
    }

    /**
     * CSVから全てのタスクデータを取得します。
     *
     * @return タスクのリスト
     * @see com.taskapp.dataaccess.UserDataAccess#findByCode(int)
     */
    public List<Task> findAll() {
        List<Task> taskList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                 String[] values = line.split(",");

                 if (values.length != 4) continue;

                 taskList.add(new Task(
                         Integer.parseInt(values[0]),
                         values[1],
                         Integer.parseInt(values[2]),
                         userDataAccess.findByCode(Integer.parseInt(values[3]))
                 ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskList;
    }

    /**
     * タスクをCSVに保存します。
     * @param task 保存するタスク
     */
     public void save(Task task) {
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) {
             writer.write(createLine(task));
             writer.newLine();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    /**
     * コードを基にタスクデータを1件取得します。
     * @param code 取得するタスクのコード
     * @return 取得したタスク
     */
     public Task findByCode(int code) {
         try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

         } catch (IOException e) {
             e.printStackTrace();
         }
         return null;
     }

    /**
     * タスクデータを更新します。
     * @param updateTask 更新するタスク
     */
     public void update(Task updateTask) {
         List<Task> taskList = findAll();
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
             for (Task task :taskList){
                 if (task.getCode() == updateTask.getCode()){
                     writer.write(createLine(updateTask));
                 }else {
                     writer.write(createLine(task));
                 }
             }

         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    /**
     * コードを基にタスクデータを削除します。
     * @param code 削除するタスクのコード
     */
    // public void delete(int code) {
    //     try () {

    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    /**
     * タスクデータをCSVに書き込むためのフォーマットを作成します。
     * @param task フォーマットを作成するタスク
     * @return CSVに書き込むためのフォーマット文字列
     */
     private String createLine(Task task) {
         return task.getCode()+","+ task.getName()+","+ task.getStatus()+","+ task.getRepUser();
     }
}