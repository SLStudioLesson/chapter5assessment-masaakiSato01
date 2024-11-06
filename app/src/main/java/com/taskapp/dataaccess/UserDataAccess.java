package com.taskapp.dataaccess;

import com.taskapp.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserDataAccess {
    private final String filePath;

    public UserDataAccess() {
        filePath = "app/src/main/resources/users.csv";
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     *
     * @param filePath
     */
    public UserDataAccess(String filePath) {
        this.filePath = filePath;
    }

    /**
     * メールアドレスとパスワードを基にユーザーデータを探します。
     *
     * @param email    メールアドレス
     * @param password パスワード
     * @return 見つかったユーザー
     */
    public User findByEmailAndPassword(String email, String password) {
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                 String[] values = line.split(",");

                 if (values.length != 4) continue;

                 if (values[2].equals(email) && values[3].equals(password)){
                     return new User(
                             Integer.parseInt(values[0]),
                             values[1],
                             values[2],
                             values[3]
                     );
                 }
            }
        } catch (IOException e) {
            e.printStackTrace();
            ;
        }
        return null;
    }

    /**
     * コードを基にユーザーデータを取得します。
     * @param code 取得するユーザーのコード
     * @return 見つかったユーザー
     */
     public User findByCode(int code) {
         try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
             String line;
             reader.readLine();
             while ((line = reader.readLine())!= null){
                 String[] values = line.split(",");

                 if (values.length != 4) continue;

                 if (Integer.parseInt(values[0]) == code){
                     return new User(
                             Integer.parseInt(values[0]),
                             values[1],
                             values[2],
                             values[3]
                     );
                 }
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
         return null;
     }
}
