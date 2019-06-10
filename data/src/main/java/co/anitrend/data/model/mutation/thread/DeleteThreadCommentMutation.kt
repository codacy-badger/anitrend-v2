package co.anitrend.data.model.mutation.thread

import co.anitrend.data.model.contract.IGraphQuery

/** [DeleteThreadComment mutation](https://anilist.github.io/ApiV2-GraphQL-Docs/mutation.doc.html)
 *
 * Delete a thread comment
 *
 * @param id The id of the thread comment to delete
 */
data class DeleteThreadCommentMutation(
    val id: Int
) : IGraphQuery {

    /**
     * A map serializer to build maps out of object so that we can consume them
     * using [io.github.wax911.library.model.request.QueryContainerBuilder].
     */
    override fun toMap() = mapOf(
        "id" to id
    )
}